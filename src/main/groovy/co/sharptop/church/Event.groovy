package co.sharptop.church

class Event extends Entry {

    String title
    String description
    List<ContentfulPerson> contactPersons
    Campus campus
    String room
    Location offCampusLocation
    Date startDate
    Date endDate
    Asset media
    SharingInfo sharingInfo

    static String contentfulContentType = "event"

}
