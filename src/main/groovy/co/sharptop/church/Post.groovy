package co.sharptop.church

class Post extends Entry {

    String title
    String summary
    Date date
    String content
    List<ContentfulPerson> people
    List<Demographic> targetDemographics
    Asset media
    SharingInfo sharingInfo
    String externalUrl
    Boolean useIframe

    static String contentfulContentType = "post"

}
