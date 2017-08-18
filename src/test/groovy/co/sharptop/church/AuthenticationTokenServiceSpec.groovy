/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.generation.SecureRandomTokenGenerator
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import spock.lang.Specification

@Mock([AuthenticationToken])
@TestFor(AuthenticationTokenService)
class AuthenticationTokenServiceSpec extends Specification {

    UserDetailsService mockUserDetailsService
    UserDetails mockUserDetails = Mock()
    SecureRandomTokenGenerator mockSecureRandomTokenGenerator = Mock()
    AccessToken mockAccessToken = Mock()

    def setup() {
        mockUserDetailsService = Mock()
        service.userDetailsService =  mockUserDetailsService

        GroovySpy(SecureRandomTokenGenerator, global: true)
    }

    def cleanup() {
    }

    void "test createToken(Person)"() {
        when:
        AuthenticationToken result = service.createToken(new Person(username: "bob"))

        then: "the token is persisted"
        result.id

        and: "it has the expected value"
        result.tokenValue == "generatedTokenValue"

        and: "the value is generated using the SecureRandomTokenGenerator"
        1 * mockUserDetailsService.loadUserByUsername("bob") >> mockUserDetails
        1 * new SecureRandomTokenGenerator() >> mockSecureRandomTokenGenerator
        1 * mockSecureRandomTokenGenerator.generateAccessToken(mockUserDetails) >> mockAccessToken
        1 * mockAccessToken.accessToken >> "generatedTokenValue"
    }

}
