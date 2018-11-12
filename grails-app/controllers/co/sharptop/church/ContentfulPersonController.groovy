package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class ContentfulPersonController extends EntryController {

    ContentfulPersonController() {
        super(ContentfulPerson)
    }

}