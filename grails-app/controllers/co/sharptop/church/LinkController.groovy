package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class LinkController extends EntryController {

    LinkController() {
        super(Link)
    }

}