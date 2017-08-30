package co.sharptop.church

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Mock(Config)
@TestFor(FeedService)
@TestMixin(GrailsUnitTestMixin)
class FeedServiceSpec extends Specification {

    EventService mockEventService

    def setup() {
        Entry.initializeData()
        Config.initializeData()
        service.contentfulService = new ContentfulService(
                readApiURL: "https://cdn.contentful.com/spaces/akvvi5g4kspm",
                readApiKey: "ed0a251f9ea4bc1a3472bfa0ca89e51c3d7fa71dbd92813695fb8c5791f94fd7"
        )

        mockEventService = Mock()
        service.eventService = mockEventService

    }

    def cleanup() {
    }

    void "test fetch"() {
        when:
        Feed result = service.fetch()

        then:
        result
        result.bannerImages
        result.events
        result.postGroups
        !result.hasMinistryGroups
        result.hasPrayerRequests
        !result.sermon
        result.songs

        and:
        1 * mockEventService.getAllEvents() >> [ new Event() ]
        0 * _
    }
}