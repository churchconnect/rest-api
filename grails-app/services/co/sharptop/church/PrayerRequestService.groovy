/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.transaction.Transactional

@Transactional
class PrayerRequestService {

    ContentfulService contentfulService
    EmailService emailService
    EmailListService emailListService

    List<PrayerRequest> fetchPublicPrayerRequests() {
        contentfulService.fetchEntries(PrayerRequest).findAll { !it.secret }
    }

    PrayerRequest create(PrayerRequest prayerRequest) {
        PrayerRequest createdPrayerRequest = contentfulService.createPrayerRequest(prayerRequest)
        sendNotification(createdPrayerRequest)
        return createdPrayerRequest
    }

    void sendNotification(PrayerRequest prayerRequest) {

        emailService.send([
            address: emailListService.fetchAddresses("Pastors"),
            subject: "Church Connect Prayer Request Submission",
            text: """
                A new prayer request has been submitted through the ChurchConnect application.
                ${
                    prayerRequest.secret ? 'This request is for pastors only.' : 'This request was added to the public prayer list.'

                }
                Name: $prayerRequest.author
                Summary: $prayerRequest.title
                Description: $prayerRequest.description
            """.stripIndent()
        ])
    }

}
