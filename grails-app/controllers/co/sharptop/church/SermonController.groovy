package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class SermonController extends EntryController {

    SermonController() {
        super(Sermon)
    }

}