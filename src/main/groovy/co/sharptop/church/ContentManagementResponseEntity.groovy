package co.sharptop.church

import org.grails.web.json.JSONObject

import java.text.ParseException

/**
 * Wraps json responses from the Contentful Content Management API, which structures data differently
 * from the Content Delivery API.
 */
class ContentManagementResponseEntity {

    JSONObject json

    ContentManagementResponseEntity(JSONObject responseJSON) {
        json = responseJSON
    }

    def resolveProperty(String name, Class type) {
        if (type == Date) {
            String dateString = resolveProperty(name)
            try {
                return ContentfulUtil.parseDate(dateString)
            } catch (ParseException e) {
                return ContentfulUtil.parseSystemDate(dateString)
            }
        }

        return resolveProperty(name)
    }

    def resolveProperty(String name) {
        if (json.fields.has(name)) {
            if (json.fields."$name".has("en-US")) {
                return json.fields."$name"."en-US"
            }

            return json.fields."$name"
        }

        if (json.sys.has(name)) {
            return json.sys."$name"
        }

        return null
    }

}

