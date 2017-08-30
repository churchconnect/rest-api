package co.sharptop.church

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Config)
class ConfigSpec extends Specification {

    def setup() {
        new Config(appName: "bacon").save(validate: false)
    }

    def cleanup() {
    }

    void "test appName"() {
        when:
        def result = Config.current.appName

        then:
        result == "bacon"
    }
}
