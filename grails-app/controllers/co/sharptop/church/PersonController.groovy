package co.sharptop.church

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.converters.*

@Secured('ROLE_USER')
class PersonController {

    SpringSecurityService springSecurityService

    def getLoggedInUser() {
        render new PersonCommand(springSecurityService.currentUser) as JSON
    }

}

class PersonCommand {
    Integer id
    String username

    PersonCommand(Person person) {
        id = person.id
        username = person.username
    }
}