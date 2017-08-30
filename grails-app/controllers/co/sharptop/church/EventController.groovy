package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class EventController extends EntryController {

    EventService eventService

    EventController() {
        super(Event)
    }

    def index() {
        List<Entry> entries = eventService.getAllEvents()

        if (!entries) {
            throw new ResourceNotFoundException()
        }

        render (entries as JSON)
    }

    def show(String id) {
        Entry entry = eventService.getEvent(id)

        if (!entry) {
            throw new ResourceNotFoundException()
        }

        render (entry as JSON)
    }
}