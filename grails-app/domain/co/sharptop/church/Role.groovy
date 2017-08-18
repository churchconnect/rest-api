/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'authority')
@ToString(includes = 'authority', includeNames = true, includePackage = false)
class Role implements Serializable {

    static final String ADMIN = "ROLE_ADMIN"
    static final String USER = "ROLE_USER"
    static final String AUTHENTICATOR = "ROLE_AUTHENTICATOR"

    private static final long serialVersionUID = 1

    String authority

    Role(String authority) {
        this()
        this.authority = authority
    }

    static constraints = {
        authority blank: false, unique: true
    }

    static mapping = {
        cache true
    }

    static void initializeData() {
        [ADMIN, USER, AUTHENTICATOR].each {
            if (!Role.findByAuthority(it)) {
                new Role(authority: it).save(failOnError: true)
            }
        }
    }

    static Role findAdmin() {
        findByAuthority(ADMIN)
    }

    static Role findUser() {
        findByAuthority(USER)
    }

    static Role findAuthenticator() {
        findByAuthority(AUTHENTICATOR)
    }

}
