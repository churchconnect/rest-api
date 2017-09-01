package co.sharptop.church

import grails.converters.JSON

import javax.transaction.Transactional

@Transactional
class FeedService {

    ContentfulService contentfulService

    EventService eventService

    JSON feedJSON

    void refreshFeedCache() {
        feedJSON = fetch() as JSON
    }

    Feed fetch() {

        new Feed(
            bannerImages: contentfulService.fetchBannerImages(),
            events: eventService.getAllEvents(),
            givingURL: "https://pushpay.com/p/thomasroadbaptistchurch",
            liveStreamLink: contentfulService.fetchEntry(Link.class, Config.current.liveStreamLinkId),
            postGroups: contentfulService.fetchPostGroups(),
            hasMinistryGroups: false, // contentfulService.fetchMinistryGroups(),
            hasPrayerRequests: contentfulService.fetchEntries(PrayerRequest),
            sermon: fetchCurrentSermon(),
            songs: fetchCurrentSongs()
        )
    }

    //TODO: set sermon to the one for the current week, rather than the first one
    Sermon fetchCurrentSermon() {
        List<Sermon> sermons = contentfulService.fetchSermons()
        sermons ? sermons.first() : null
    }

    //TODO: determine the current song list, rather than just grabbing the first one.
    List<Song> fetchCurrentSongs() {
        List<SongList> songLists = contentfulService.fetchSongLists()
        songLists ? songLists.first()?.songs : null
    }
}
