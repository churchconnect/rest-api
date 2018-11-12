package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class PostController extends EntryController {

    PostController() {
        super(Post)
    }

}

