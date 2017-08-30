package co.sharptop.church

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class BannerImageController extends EntryController {

    BannerImageController() {
        super(BannerImage)
    }

}