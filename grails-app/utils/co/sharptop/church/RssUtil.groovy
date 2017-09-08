package co.sharptop.church

import org.grails.web.json.JSONObject
import java.text.SimpleDateFormat

class RssUtil {
    String rssMetadata
    String rssPostIdBase
    JSONObject rssJsonData

    RssUtil(rssMetadata, rssPostIdBase) {
        this.rssMetadata = rssMetadata
        this.rssPostIdBase = rssPostIdBase
        this.rssJsonData = new JSONObject(this.rssMetadata)
    }

    List<Post> createRssPosts() {
        def rssUrl = rssJsonData.url
        def rootRss = new XmlSlurper().parse(rssUrl)
        def rssMetadataFields = rssJsonData.fields

        rootRss.channel.item.withIndex().collect { item, index ->
            createRssPost(rssMetadataFields, item, index)
        }
    }

    Post createRssPost(rssMetadataFields, item, index) {
        def imageForSummaryPath = rssJsonData.imageForSummaryPath
        def seperator = rssJsonData.attributeSeperator
        def dateStringFormat = rssJsonData.dateFormat
        def title = concatMetadataValues(rssMetadataFields.title.attributes, item, seperator)
        def summary = concatMetadataValues(rssMetadataFields.summary.attributes, item, seperator)
        def dateString = concatMetadataValues(rssMetadataFields.date.attributes, item, seperator)
        def content = concatMetadataValues(rssMetadataFields.content.attributes, item, seperator)
        def media = null

        if(imageForSummaryPath) {
            summary = generateImageString(objectBinder(item, imageForSummaryPath))
        }

        if(rssMetadataFields.media) {
            def mediaAttrs = rssMetadataFields.media

            if(mediaAttrs) {
                media = new Asset(
                        title: concatMetadataValues(mediaAttrs.title.attributes, item, seperator),
                        url: concatMetadataValues(mediaAttrs.url.attributes, item, seperator),
                        contentType: getMediaContentType(),
                        width: concatMetadataValues(mediaAttrs.width.attributes, item, seperator).toInteger(),
                        height: concatMetadataValues(mediaAttrs.height.attributes, item, seperator).toInteger()
                )
            }
        }

        /**
         * MD5 Id generated with a Seed word that WONT change. Hence same MD5 on each request
         */
        new Post(
                id: HashUtil.generateMD5("${rssPostIdBase}-${index}"),
                title: title,
                summary: summary,
                date: new SimpleDateFormat(dateStringFormat).parse(dateString),
                content: content,
                media: media
        )
    }

    String getMediaContentType() {
        'image/webp'
    }

    String generateImageString(src) {
        return "<img src='${src}' />"
    }

    String concatMetadataValues(metadataAttributes, rssPost, seperator = "") {
        metadataAttributes.collect { objectBinder(rssPost, it.target) }.join(seperator)
    }

    def objectBinder(doc, path) {
        def nodes = doc
        path.split("\\.").each { nodes = nodes."${it}" }
        return nodes
    }
}