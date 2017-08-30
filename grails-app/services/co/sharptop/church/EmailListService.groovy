package co.sharptop.church

import grails.transaction.Transactional

@Transactional
class EmailListService {

    ContentfulService contentfulService

    EmailList findByTitle(String title) {
        contentfulService.fetchEntries(EmailList).find { it.title == title}
    }

    List<String> fetchAddresses(EmailList emailList) {
        emailList.recipients*.email - null
    }

    List<String> fetchAddresses(String title) {
        fetchAddresses(findByTitle(title))

    }

}
