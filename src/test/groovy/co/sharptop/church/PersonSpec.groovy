/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

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