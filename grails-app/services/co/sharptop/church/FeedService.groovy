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
    @Value('${church.prayerTimeImageAssetID:}')
    prayerTimeImageAssetID

    @Value('${church.eventsImageAssetID:}')
    eventsImageAssetID

    @Value('${church.liveStreamLinkId:}')
    liveStreamLinkId

    @Value('${church.givingLinkId:}')
    givingLinkId

    JSON feedJSON

    void refreshFeedCache() {
        feedJSON = fetch() as JSON
    }

    Feed fetch() {

        // Defaults we can pass without worry
        def feedDefaults = [
            bannerImages: contentfulService.fetchBannerImages(),
            events: eventService.getAllEvents(),
            postGroups: contentfulService.fetchPostGroups(),
            hasMinistryGroups: false, // contentfulService.fetchMinistryGroups(),
            hasPrayerRequests: contentfulService.fetchPrayerRequests(),
            sermon: fetchCurrentSermon(),
            songs: fetchCurrentSongs()
        ]

        // Optional fields that are set in the application-*.yml. If they are filled we call the correct function and add it
        def feedOptionalInfoMap = [
            liveStreamLink: [
                id: liveStreamLinkId,
                func: contentfulService.&fetchLink
            ],
            givingURL: [
                id: givingLinkId,
                func: contentfulService.&fetchLink
            ],
            prayerTimeImageURL: [
                id: prayerTimeImageAssetID,
                func: contentfulService.&fetchAsset,
                prop: "url"
            ],
            eventsImageURL: [
                id: eventsImageAssetID,
                func: contentfulService.&fetchAsset,
                prop: "url"
            ]
        ]

        new Feed(mapOptionalsToFeedProps(feedOptionalInfoMap, feedDefaults))
    }

    Map mapOptionalsToFeedProps(optionals, feedDefaults) {
        // Loop the optionals and add it to the defaults
        optionals.each{ k, v ->
            if(v.id != "") {
                def respValue = v.func(v.id)

                feedDefaults."$k" = (v.prop != null) ? respValue?."${v.prop}" : respValue
            }
        }

        feedDefaults
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
