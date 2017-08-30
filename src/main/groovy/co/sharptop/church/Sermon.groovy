package co.sharptop.church

class Sermon extends Entry {

    String title
    String content
    Asset image
    Asset video
    Asset audio
    List<ContentfulPerson> speakers
    List<Demographic> demographics
    SharingInfo sharingInfo

    static String contentfulContentType = "sermon"

}
