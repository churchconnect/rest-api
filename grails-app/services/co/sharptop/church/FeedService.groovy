package co.sharptop.church

import grails.converters.JSON
import org.springframework.beans.factory.annotation.Value

import javax.transaction.Transactional

@Transactional
class FeedService {

    ContentfulService contentfulService

    EventService eventService

    @Value('${church.settingsID:}')
    String settingsID

    JSON feedJSON

    void refreshFeedCache() {
        feedJSON = fetch() as JSON
    }

    Feed fetch() {
        Settings settings = contentfulService.fetchSettings(settingsID)

        new Feed(
            settings: settings,
            bannerImages: contentfulService.fetchBannerImages(),
            events: eventService.getAllEvents(settings.eventICalLink),
            postGroups: contentfulService.fetchPostGroups(),
            hasMinistryGroups: (!settings.ministryGroups) ? false : contentfulService.fetchMinistryGroups(),
            hasPrayerRequests: contentfulService.fetchPrayerRequests(),
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
