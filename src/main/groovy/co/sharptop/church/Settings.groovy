package co.sharptop.church

class Settings extends Entry {

    String appName
    Link liveStreamLink
    Link givingLink
    Link eventICalLink
    Asset prayerTimeBanner
    Asset eventBanner
    Boolean ministryGroups
    String googleMapsApiKey

    static String contentfulContentType = "settings"
}
