package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class SongController extends EntryController {

    SongController() {
        super(Song)
    }

}
