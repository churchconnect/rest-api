/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.plugin.springsecurity.rest.token.generation.SecureRandomTokenGenerator
import grails.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

@Transactional
class AuthenticationTokenService {

    UserDetailsService userDetailsService

    AuthenticationToken createToken(String username) {
        createToken(Person.findOrCreate(username))
    }

    AuthenticationToken createToken(Person person) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(person.username)

        AuthenticationToken token = new AuthenticationToken(
            username: person.username,
            tokenValue: generateTokenValue(userDetails)
        ).save(failOnError: true)

        return token
    }

    private String generateTokenValue(UserDetails userDetails) {
        new SecureRandomTokenGenerator().generateAccessToken(userDetails).accessToken
    }

}
