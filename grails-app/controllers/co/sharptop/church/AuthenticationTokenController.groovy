/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.ACCEPTED
import static org.springframework.http.HttpStatus.UNAUTHORIZED

@Secured('ROLE_AUTHENTICATOR')
class AuthenticationTokenController extends RestfulController {

    ExternalAuthenticationService externalAuthenticationService
    AuthenticationTokenService authenticationTokenService

    AuthenticationTokenController() {
        super(AuthenticationToken)
    }

    @Secured('permitAll')
    def save(CreateAuthenticationTokenCommand authenticationTokenCommand) {
        String username = externalAuthenticationService.getUsername(authenticationTokenCommand.token, authenticationTokenCommand.service)
        AuthenticationToken token = authenticationTokenService.createToken(username)

        if (authenticationTokenCommand.service == "email") {
            externalAuthenticationService.deliverTokenViaEmail(token)
            response.status = ACCEPTED.value()
            render "Login link sent via email to $token.username"
            return
        }

        render token as JSON
    }

    def handleExternalAuthenticationFailureException(final ExternalAuthenticationFailureException e) {
        response.status = UNAUTHORIZED.value()
        render([message: "Token failed to authenticate against $e.service"] as JSON)
    }

}

class CreateAuthenticationTokenCommand {
    String service
    String token
}
