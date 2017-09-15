package co.sharptop.church

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Value

import javax.transaction.Transactional

@Transactional
class FeedService {

    ContentfulService contentfulService

    EventService eventService

    //TODO: move these configs into a ConfigService (to replace Config.groovy -- we want config to come from environment, not database)
    //TODO: include application configuration from ConfigService.groovy...
    @Value('${church.prayerTimeImageAssetID}')
    prayerTimeImageAssetID
    @Value('${church.eventsImageAssetID}')
    eventsImageAssetID
    @Value('${church.liveStreamLinkId}')
    liveStreamLinkId

    JSON feedJSON

    void refreshFeedCache() {
        feedJSON = fetch() as JSON
    }

    Feed fetch() {
        new Feed(
            bannerImages: contentfulService.fetchBannerImages(),
            events: eventService.getAllEvents(),
            givingURL: "https://pushpay.com/p/thomasroadbaptistchurch",
            liveStreamLink: contentfulService.fetchLink(liveStreamLinkId),
            postGroups: contentfulService.fetchPostGroups(),
            hasMinistryGroups: false, // contentfulService.fetchMinistryGroups(),
            hasPrayerRequests: contentfulService.fetchPrayerRequests(),
            prayerTimeImageURL: contentfulService.fetchAsset(prayerTimeImageAssetID)?.url,
            eventsImageURL: contentfulService.fetchAsset(eventsImageAssetID)?.url,
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
