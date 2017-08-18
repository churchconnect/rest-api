/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

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