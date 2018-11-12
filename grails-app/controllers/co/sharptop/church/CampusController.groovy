package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class CampusController extends EntryController {

    CampusController() {
        super(Campus)
    }

}
