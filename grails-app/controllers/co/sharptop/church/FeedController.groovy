package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class FeedController {

    FeedService feedService

    def index() {
        if (!feedService.feedJSON) {
            log.error "Feed has not been stored yet."
            response.status = 404
            return
        }

        render feedService.feedJSON
    }

}
