package co.sharptop.church

import grails.transaction.Transactional

@Transactional
class RssPostService {

    ContentfulService contentfulService
    Map cache = [:]

    def fetch(PostGroup postGroup) {
        if (!postGroup.rssMetadata) {
            return []
        }

        if (!cache[postGroup.id]) {
            refreshFeed(postGroup)
        }

        return cache[postGroup.id]
    }

    void refreshFeeds() {
        log.info "Refreshing RSS Feeds"
        contentfulService.fetchEntries(PostGroup).findAll {
            it.rssMetadata
        }.each { PostGroup postGroup ->
            refreshFeed(postGroup)
        }
        log.info "Done refreshing RSS Feeds"
    }

    void refreshFeed(PostGroup postGroup) {
        cache[postGroup.id] = new RssUtil(postGroup.rssMetadata, "rssPost-${postGroup.id}").createRssPosts()
    }

}
