package co.sharptop.church

import org.grails.web.json.JSONObject

class Asset extends Entry {

    String title
    String url
    String contentType
    //TODO: figure out how to separate image assets from non-image assets.
    Integer width
    Integer height

    static String contentfulContentType = "asset"

    static JSONObject preprocessJSON(JSONObject jsonObject) {
        new JSONObject(
            [
                title: jsonObject.title,
                url: jsonObject.file.url,
                contentType: jsonObject.file.contentType,
                width: jsonObject.file.details?.image?.width,
                height: jsonObject.file.details?.image?.height
            ]
        )
    }

    String getUrl() {
        url.startsWith('//') ? "https:$url" : url
    }

}