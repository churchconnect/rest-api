package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class PostGroupController extends EntryController {

    PostGroupController() {
        super(PostGroup)
    }

}