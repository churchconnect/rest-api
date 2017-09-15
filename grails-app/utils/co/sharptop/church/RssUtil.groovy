package co.sharptop.church

import groovy.util.slurpersupport.GPathResult
import org.grails.web.json.JSONObject
import java.text.SimpleDateFormat
import groovy.util.logging.Slf4j

@Slf4j
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
        def rootRss = getXmlResult(rssJsonData.url, rssJsonData.namespaces)

        rootRss.channel.item.withIndex().collect { item, index ->
            try {
                createRssPost(rssJsonData.fields, item, index)
            } catch(Exception e) {
                log.error "Exception while creating Post for RSS Post Group ${rssPostIdBase}:"
                log.error "Message: $e.message"
            }
        }
    }

    GPathResult getXmlResult(url, namespaces) {
        Map out = [:]

        namespaces.each {
            out[it.name] = it.url
        }

        new XmlSlurper(false, true).parse(url).declareNamespace(out)
    }

    Post createRssPost(rssMetadataFields, rssItem, index) {
        // Top level JSON data
        def imageForSummaryPath = rssJsonData.imageForSummaryPath       // TODO: Might get rid of this
        def seperator = rssJsonData.attributeSeperator
        def dateStringFormat = rssJsonData.dateFormat

        // Json field level (titled fields in the json, see createRssPosts)
        def title = concatMetadataValues(rssMetadataFields.title.attributes, rssItem, seperator)
        def summary = concatMetadataValues(rssMetadataFields.summary.attributes, rssItem, seperator)
        def dateString = concatMetadataValues(rssMetadataFields.date.attributes, rssItem, seperator)
        def content = concatMetadataValues(rssMetadataFields.content.attributes, rssItem, seperator)
        def media = null
        List<Map> extraFields = []

        // extraFields handling, maybe move into separate function later
        if(rssJsonData.extraFields) {

            // For each extraFields
            rssJsonData.extraFields.each { extraField ->
                def tempMap = [:]

                // Loop through each attached property and value on the extraField
                extraField.each { prop, value ->

                    // If the property name is attribute, we need to make a map
                    if (prop == "attribute") {
                        def targetValue = objectBinder(rssItem, value.target)
                        def type = value.type

                        value = ["targetValue": targetValue.toString(), "type": type.toString()]
                    // Else make sure the value is a string
                    } else {
                        value = value.toString()
                    }

                    // Assign the value to the property on a new map
                    tempMap[prop.toString()] = value
                }

                extraFields.add(tempMap)
            }
        }

        if(imageForSummaryPath) {
            summary = generateImageString(objectBinder(rssItem, imageForSummaryPath))
        }

        if(rssMetadataFields.media) {
            def mediaAttrs = rssMetadataFields.media

            if(mediaAttrs) {
                def mediaContentTypeValue = (mediaAttrs.contentType.value) ? mediaAttrs.contentType.value : concatMetadataValues(mediaAttrs.contentType.attributes, rssItem, seperator)
                def mediaContentWidth = (mediaAttrs.width.value) ? mediaAttrs.width.value : concatMetadataValues(mediaAttrs.width.attributes, rssItem, seperator)
                def mediaContentHeight = (mediaAttrs.height.value) ? mediaAttrs.height.value : concatMetadataValues(mediaAttrs.height.attributes, rssItem, seperator)

                media = new Asset(
                    title: concatMetadataValues(mediaAttrs.title.attributes, rssItem, seperator),
                    url: concatMetadataValues(mediaAttrs.url.attributes, rssItem, seperator),
                    contentType: mediaContentTypeValue,
                    width: mediaContentWidth,
                    height: mediaContentHeight
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
            media: media,
            extraFields: extraFields
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
        path.split("\\.").each {
            nodes = nodes."${it}"
        }
        return nodes
    }
}