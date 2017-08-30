package co.sharptop.church

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(PrayerRequestService)
class PrayerRequestServiceSpec extends Specification {

    ContentfulService mockContentfulService

    def setup() {
        mockContentfulService = Mock()
        service.contentfulService = mockContentfulService
    }

    def cleanup() {
    }

    void "test fetchPublicPrayerRequests"() {
        setup:
        List<PrayerRequest> prayerRequests = [
            new PrayerRequest(title: "secret bacon", author: "chef", description: "I need bacon", secret: true),
            new PrayerRequest(title: "public bacon", author: "chef", description: "I need bacon", secret: false),
        ]

        when:
        def results = service.fetchPublicPrayerRequests()

        then:
        1 * mockContentfulService.fetchEntries(PrayerRequest) >> prayerRequests

        and:
        results.size() == 1
        results.first().title == "public bacon"
    }

}
