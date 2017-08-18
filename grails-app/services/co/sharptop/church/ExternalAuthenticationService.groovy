/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value

import static org.springframework.http.HttpStatus.OK

@Transactional
class ExternalAuthenticationService {

    EmailService emailService

    @Value('${church.authentication.external.routerURL}')
    String routerURL

    RestBuilder rest = new RestBuilder()

    String getUsername(String token, String service) {
        if (service?.toLowerCase() == "email") {
            return token
        }

        //TODO: this check will need to be moved into the router
        if (service?.toLowerCase() == "facebook") {
            return getFacebookUsername(token)
        }

        RestResponse response = rest.post("$routerURL/") {
            accept("application/json")
            contentType("application/json")
            json([token: token, service: service] as JSON)
        }

        return response.json.username
    }

    //TODO: this will need to be moved into the router.
    String getFacebookUsername(String token) {
        RestResponse response = rest.post("$routerURL/j_spring_security_facebook_json?access_token=$token") {
            accept("application/json")
        }

        if (response.statusCode != OK || !response.json?.username) {
            throw new ExternalAuthenticationFailureException("facebook")
        }

        return response.json.username
    }

    void deliverTokenViaEmail(AuthenticationToken token) {
        sendBacon(token.username, token.tokenValue)
    }


    void sendBacon(String emailAddress, String tokenValue) {

        emailService.send([
            address: emailAddress,
            subject: "Login to $Config.current.appName",
            html   : """
                <a href="$Config.current.appURL/#/login/$tokenValue">Click here to Login</a>
            """.stripIndent()
        ])
    }

}


