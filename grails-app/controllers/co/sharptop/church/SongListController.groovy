package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class SongListController extends EntryController {

    SongListController() {
        super(SongList)
    }

}