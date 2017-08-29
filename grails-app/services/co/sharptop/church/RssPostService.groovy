package co.sharptop.church

import grails.transaction.Transactional

@Transactional
class RssPostService {

    Map cache = [:]

    def fetch(PostGroup postGroup) {
        if (!postGroup.rssMetadata) {
            return []
        }

        if (!cache[postGroup.id]) {
            cache[postGroup.id] = new RssUtil(postGroup.rssMetadata, "rssPost-${postGroup.id}").createRssPosts()
        }

        return cache[postGroup.id]
    }
}
