package co.sharptop.church

import org.springframework.scheduling.annotation.Scheduled

/**
 * Class to schedule the synchronized refresh of all caches on the server.
 */
class CacheService {

    boolean lazyInit = false

    FeedService feedService
    RssPostService rssPostService

    @Scheduled(fixedDelayString = '${church.cacheInterval}', initialDelayString = '${church.cacheInitialDelay}')
    void refreshCaches() {
        log.info "Refreshing caches"
        rssPostService.refreshFeeds()
        feedService.refreshFeedCache()
        log.info "Done refreshing caches"
    }
}
