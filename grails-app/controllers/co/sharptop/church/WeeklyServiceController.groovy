package co.sharptop.church


import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class WeeklyServiceController extends EntryController {

    WeeklyServiceController() {
        super(WeeklyService)
    }

}