package co.sharptop.church

import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

/**
 * Contentful doesn't render the full JSON of the object tree when you get entries. Instead, each related object
 * is placed in an "includes" property. Under includes you have "Asset" and "Entry". We use this class to search
 * for linked objects so that we can reconstruct the object tree.
 * One other issue is that sometimes a Link will be to an entry not in the includes property, but another item of the
 * same type as the original. In order for this link to resolve, we need to have the items json included with the
 * includes. If when looking for an entry we don't find it in the "includes.Entry" array, we search in the items array.
 */
class ContentfulIncludes {

    JSONObject includes
    JSONArray items

    ContentfulIncludes(JSONObject includesJSON, JSONArray itemsJSON) {
        includes = includesJSON
        items = itemsJSON
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

        if (!fields) {
            fields = items.find { it.sys.id == id }?.fields
        }

        fields?.put("id", id)
        return fields
    }

    JSONObject getAsset(String id) {
        JSONObject fields = includes.Asset?.find { it.sys.id == id }?.fields
        fields?.put("id", id)
        return fields
    }

}
