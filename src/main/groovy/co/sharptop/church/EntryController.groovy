package co.sharptop.church

import grails.artefact.Artefact
import grails.converters.JSON

@Artefact("Controller")
class EntryController<T extends Entry> implements ResourceNotFoundHandlerTrait {

    Class<T> resource
    ContentfulService contentfulService

    EntryController(Class<T> resource) {
        this.resource = resource
    }

    def index() {
        List<Entry> entries = contentfulService.fetchEntries(resource)

        if (!entries) {
            throw new ResourceNotFoundException()
        }

        render (entries as JSON)
    }

    def show(String id) {
        Entry entry = contentfulService.fetchEntry(resource, id)

        if (!entry) {
            throw new ResourceNotFoundException()
        }

        render (entry as JSON)
    }

}
