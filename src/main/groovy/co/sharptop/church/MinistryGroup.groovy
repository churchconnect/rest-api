package co.sharptop.church

class MinistryGroup extends Entry {

    String title
    String description
    List<ContentfulPerson> contactPersons
    List<Demographic> demographics
    String meetingTime
    Location location
    SharingInfo sharingInfo

    static String contentfulContentType = "ministryGroup"

}
