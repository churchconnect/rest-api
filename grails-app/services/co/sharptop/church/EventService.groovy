package co.sharptop.church

import grails.transaction.Transactional
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Calendar
import org.springframework.beans.factory.annotation.Value

import java.text.SimpleDateFormat

@Transactional
class EventService {

    ContentfulService contentfulService

    @Value('${eventsICalFile}')
    String eventsICalFileUrl

    Event getEvent(id) {
        allEvents.find { it.id == id }
    }

    List<Event> getEvents() {
        contentfulService.fetchEvents()
    }

    List<Event> getAllEvents() {
        ICalEvents + events
    }

    List<Event> getICalEvents() {
        def url = new URL(eventsICalFileUrl)
        CalendarBuilder builder = new CalendarBuilder()
        def calEvents = []

        try {
            Calendar calendar = builder.build(new StringReader(url.getText()))

            def propMap = [
                    "startDate"  : "DTSTART",
                    "description": "DESCRIPTION",
                    "createdAt"  : "DTSTAMP",
                    "title"      : "SUMMARY",
                    "location"   : "LOCATION"
            ]

            calendar.getComponents("VEVENT").each { component ->
                def calValues = [:]
                def count = 0
                def categories = ""

                component.getProperties().each { property ->
                    propMap.each { eventProp, calProp ->
                        if (property.getName() == calProp) {
                            def value = property.getValue()
                            def compareDateValue
                            def df = new SimpleDateFormat("yyyyMMdd'T'HHmmss")
                            def today = new Date()
                            def weekFromNow = new Date() + 7

                            if (property.getName() == "DTSTART" || property.getName() == "DTEND" || property.getName() == "DTSTAMP") {
                                value = df.parse(value)
                            }

                            if(property.getName() == "DTSTART") {
                                if(value < weekFromNow && value > today) {
                                    categories = "This Week"
                                }
                            }

                            calValues[eventProp] = value
                        }
                    }
                }

                calEvents << new Event(
                        id: HashUtil.generateMD5("event-${calValues["title"]}-${count}"),
                        title: calValues["title"],
                        description: calValues["description"],
                        startDate: calValues["startDate"],
                        contactPersons: [],
                        createdAt: calValues["createdAt"],
                        updatedAt: calValues["createdAt"],
                        categories: categories,
                        location: calValues["location"]
                )

                count++
            }
        } catch(ex) {
            log.error "Error processing iCal feed ${eventsICalFileUrl}"
            log.error ex.message
        }

        calEvents
    }
}
