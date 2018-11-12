package co.sharptop.church

class Song extends Entry {

    String title
    String artist
    Asset media
    Link amazonLink
    Link iTunesLink
    Link spotifyLink
    Link youTubeLink
    Link lyricsLink

    static String contentfulContentType = "song"

}
