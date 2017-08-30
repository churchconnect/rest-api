package co.sharptop.church

import org.grails.web.json.JSONObject

class ContentfulIncludes {

    JSONObject includes

    ContentfulIncludes(JSONObject includesJSON) {
        includes = includesJSON
    }

    JSONObject resolve(JSONObject reference) {
        if (reference?.sys?.linkType == "Entry") {
            return getEntry(reference.sys.id)
        } else if (reference?.sys?.linkType == "Asset") {
            return getAsset(reference.sys.id)
        } else {
            return reference
        }
    }

    JSONObject getEntry(String id) {
        JSONObject fields = includes.Entry?.find { it.sys.id == id }?.fields
        fields?.put("id", id)
        return fields
    }

    JSONObject getAsset(String id) {
        JSONObject fields = includes.Asset?.find { it.sys.id == id }?.fields
        fields?.put("id", id)
        return fields
    }

}
