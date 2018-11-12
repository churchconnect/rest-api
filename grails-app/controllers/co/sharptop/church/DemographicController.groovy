package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class DemographicController extends EntryController {

    DemographicController() {
        super(Demographic)
    }

}