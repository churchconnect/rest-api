package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class MinistryGroupController extends EntryController {

    MinistryGroupController() {
        super(MinistryGroup)
    }

}