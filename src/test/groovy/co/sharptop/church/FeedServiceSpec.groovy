/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@Mock(Config)
@TestFor(FeedService)
@TestMixin(GrailsUnitTestMixin)
class FeedServiceSpec extends Specification {

    def setup() {
        Entry.initializeData()
        Config.initializeData()
        service.contentfulService = new ContentfulService(readApiURL: "https://cdn.contentful.com/spaces/og0cae44jgie", readApiKey: "7a90026a74e435c32abf99cd6f7f7676449de92fc0c44051bf86fd326e7fe60d")
    }

    def cleanup() {
    }

    void "test fetch"() {
        when:
        Feed result = service.fetch()

        then:
        result
        result.bannerImages
        result.events
        result.postGroups
        !result.hasMinistryGroups
        result.hasPrayerRequests
        result.sermon
        result.songs
    }
}