package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable
import org.springframework.http.HttpStatus

@Secured("permitAll")
class PrayerRequestController extends EntryController {

    PrayerRequestService prayerRequestService

    PrayerRequestController() {
        super(PrayerRequest)
    }

    def index() {
        List<PrayerRequest> prayerRequests = prayerRequestService.fetchPublicPrayerRequests()
        render (prayerRequests as JSON)
    }

    //TODO: make generic methods on EntryController for save, but use a "ReadOnly" flag to turn them off by default (when you have to add a save method to another controller)
    def save(PrayerRequestCommand prayerRequestCommand) {
        if (prayerRequestCommand.hasErrors()) {
            response.status = HttpStatus.BAD_REQUEST.value()
            render (prayerRequestCommand.errors as JSON)
            return
        }

        PrayerRequest prayerRequest = prayerRequestService.create(prayerRequestCommand.generatePrayerRequest())

        response.status = HttpStatus.CREATED.value()
        render (prayerRequest as JSON)
    }

}

class PrayerRequestCommand implements Validateable {
    String title
    String description
    String author
    boolean secret = true

    static constraints = {
        description blank: false
        author blank: false
    }

    PrayerRequest generatePrayerRequest() {
        return new PrayerRequest(
            title: title,
            description: description,
            secret: secret,
            author: author
        )
    }
}