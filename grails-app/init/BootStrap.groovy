import co.sharptop.church.*
import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->

        JSON.registerObjectMarshaller(Entry) {
            it.properties + [class: it.class.name]
        }

        Role.initializeData()
        Entry.initializeData()
        Config.initializeData()

        if (!Person.count) {
            Role admin = Role.findAdmin()
            Role authenticator = Role.findAuthenticator()
            Role user = Role.findUser()

            Person bacon = new Person(username: 'bacon', password: '.', name: 'Bacon').save(failOnError: true)
            new PersonRole(person: bacon, role: admin).save(failOnError: true)
            new PersonRole(person: bacon, role: authenticator).save(failOnError: true)
            new PersonRole(person: bacon, role: user).save(failOnError: true)

            Person userPerson = new Person(username: 'user', password: '.', name: 'User').save(failOnError: true)
            new PersonRole(person: userPerson, role: user).save(failOnError: true)

            Person authenticatorPerson = new Person(username: 'authenticator', password: '.', name: 'Authenticator').save(failOnError: true)
            new PersonRole(person: authenticatorPerson, role: authenticator).save(failOnError: true)
        }
    }
    def destroy = {
    }
}
