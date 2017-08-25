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
                    "title"      : "SUMMARY"
            ]

            calendar.getComponents("VEVENT").each { component ->
                def calValues = [:]
                def count = 0

                component.getProperties().each { property ->
                    propMap.each { eventProp, calProp ->
                        if (property.getName() == calProp) {
                            def value = property.getValue()
                            def df = new SimpleDateFormat("yyyyMMdd'T'HHmmss")

                            if (property.getName() == "DTSTART" || property.getName() == "DTEND" || property.getName() == "DTSTAMP") {
                                value = df.parse(value)
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
                        updatedAt: calValues["createdAt"]
                )

                count++
            }
        } catch(ex) {
            println("Error: Invalid ICal URL")
        }

        calEvents
    }
}
