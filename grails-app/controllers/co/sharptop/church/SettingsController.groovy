package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class SettingsController extends EntryController{

    SettingsController() {
        super(Settings)
    }

}
