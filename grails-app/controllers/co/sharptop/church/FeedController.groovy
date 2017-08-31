package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class FeedController {

    FeedService feedService

    def index() {
        render feedService.feedJSON
    }

}
