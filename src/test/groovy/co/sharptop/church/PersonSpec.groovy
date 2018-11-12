package co.sharptop.church

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.*

@Mock([Person, PersonRole, Role])
@TestMixin(GrailsUnitTestMixin)
class PersonSpec extends Specification {

    def setup() {
        Role.initializeData()
    }

    def cleanup() {
    }

    void "test findOrCreate returns an existing user"() {
        given: "A Person named bob"
        Person bob = new Person(username: "bob").save(validate: false)

        expect: "findOrCreate returns bob"
        Person.findOrCreate("bob").id == bob.id
    }

    void "test findOrCreate creates a new user"() {
        setup:
        assert !Person.findByUsername("bacon")

        when:
        Person.findOrCreate("bacon")

        then:
        Person.findByUsername("bacon")

        and: "the person has a role of user"
        Person.findByUsername("bacon").hasRole(Role.findUser())
    }

}