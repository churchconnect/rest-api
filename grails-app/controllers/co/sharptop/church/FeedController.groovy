package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured('permitAll')
class FeedController {

    FeedService feedService

    def index() {
        render(feedService.fetch() as JSON)
    }

}
