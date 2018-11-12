package co.sharptop.church

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Mock(Config)
@TestFor(FeedService)
@TestMixin(GrailsUnitTestMixin)
class FeedServiceSpec extends Specification {

    EventService mockEventService
    Settings settings

    def setup() {
        Entry.initializeData()
        Config.initializeData()
        service.contentfulService = new ContentfulService(
                readApiURL: "http://www.google.com",
                readApiKey: "fasdfjlkshfuwefalkjsfskadjf"
        )
        settings = new Settings(
                appName: "TestApp",
                liveStreamLink: null,
                givingLink: null,
                eventICalLink: Mock(Link),
                prayerTimeBanner: null,
                eventBanner: null,
                ministryGroups: false
        )
        service.contentfulService.metaClass.fetchSettings = { id -> return settings }
        service.contentfulService.metaClass.fetchPrayerRequests = { -> return [ Mock(PrayerRequest) ] }

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
        result.settings
        result.bannerImages == []
        result.events
        result.postGroups == []
        !result.hasMinistryGroups
        result.hasPrayerRequests
        !result.sermon
        !result.songs

        and:
        1 * mockEventService.getAllEvents(settings.eventICalLink) >> [ new Event() ]
        0 * _
    }
}