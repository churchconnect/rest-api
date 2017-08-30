package co.sharptop.church

import grails.util.Holders

class PostGroup extends Entry {

    String title
    Asset media
    List<Post> posts
    String rssUrl
    List<Post> publishedPosts
    String rssMetadata

    static String contentfulContentType = "post-group"

    List<Post> getPublishedPosts() {
        rssPosts + contentfulPosts
    }

    List<Post> getContentfulPosts() {
        posts.findAll {
            it && (!it.date || it.date < new Date())
        }
    }

    private List<Post> getRssPosts() {
        RssPostService rssPostService = Holders.grailsApplication.mainContext.getBean('rssPostService')
        rssPostService.fetch(this)
    }
}
