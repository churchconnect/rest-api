package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class FeedController {

    FeedService feedService

    def index() {
        if (!feedService.feedJSON) {
            log.error "Feed has not been stored yet."
            response.status = 404
            render([message:"Feed has not been generated yet."] as JSON)
            return
        }

        render feedService.feedJSON
    }

}
