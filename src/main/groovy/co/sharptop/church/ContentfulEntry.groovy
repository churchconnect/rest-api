package co.sharptop.church

import org.grails.web.json.JSONObject

class ContentfulEntry {

    JSONObject json

    ContentfulEntry(JSONObject jsonObject) {
        json = jsonObject
    }

    String getId() {
        json.sys.id
    }

    String getContentType() {
        json.sys.contentType.sys.id
    }

    String getCreatedAt() {
        json.sys.createdAt
    }

    String getUpdatedAt() {
        json.sys.updatedAt
    }

    JSONObject getFields() {
        JSONObject fields = json.fields
        fields.put("id", id)
        return fields
    }

}
