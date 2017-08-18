/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.transaction.Transactional

@Transactional
class FeedService {

    ContentfulService contentfulService

    Feed fetch() {
        new Feed(
            bannerImages: contentfulService.fetchBannerImages(),
            events: contentfulService.fetchEvents(),
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
