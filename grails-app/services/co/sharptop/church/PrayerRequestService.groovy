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
