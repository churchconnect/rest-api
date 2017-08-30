package co.sharptop.church

class PrayerRequest extends Entry {

    String title
    boolean secret
    String description
    String author

    static String contentfulContentType = "prayerRequest"

}
