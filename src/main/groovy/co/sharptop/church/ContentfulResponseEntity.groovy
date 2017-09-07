package co.sharptop.church

import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

import java.lang.reflect.Field

class ContentfulResponseEntity {

    List<ContentfulEntry> entries
    ContentfulIncludes includes

    ContentfulResponseEntity(JSONObject responseJSON) {
        includes = new ContentfulIncludes(responseJSON.includes, responseJSON.items)
        entries = responseJSON.items.collect {
            new ContentfulEntry(it)
        }
    }

    List<Entry> buildObjects() {
        entries.collect { entry ->
            Class clazz = Entry.getClassForContentType(entry.contentType)

            if (!clazz) {
                return null
            }

            Entry entryObject = buildObject(clazz, entry.fields)

            addSystemProperties(entryObject, entry)
        }
    }

    Entry addSystemProperties(Entry entry, ContentfulEntry contentfulEntry) {
        entry.createdAt = ContentfulUtil.parseSystemDate(contentfulEntry.createdAt)
        entry.updatedAt = ContentfulUtil.parseSystemDate(contentfulEntry.updatedAt)
        return entry
    }

    //TODO: This will freak out if we have a List of non-entry type objects on our domain class.
    //e.g. List<String>.
    List<Entry> buildList(Class<Entry> clazz, JSONArray objects) {
        objects.collect { JSONObject fields ->
            buildObject(clazz, fields)
        }
    }

    Entry buildObject(Class<Entry> clazz, JSONObject fields) {
        def objectMap = buildObjectMap(clazz, fields)
        return objectMap ? clazz.newInstance(objectMap) : null
    }

    Map buildObjectMap(Class clazz, JSONObject fields) {
        fields = processJSON(clazz, fields)

        // we have to process the JSON before we can know for sure if the object is actually there
        if (!fields) {
            return null
        }

        ClassUtil.getTrueFields(clazz, 1).collectEntries {
            [(it.name): buildValue(it, fields)]
        }
    }

    JSONObject processJSON(Class<Entry> clazz, JSONObject json) {
        json = includes.resolve(json)
        return json ? clazz.preprocessJSON(json) : null
    }

    def buildValue(Field field, JSONObject fields) {
        if (field.type == Date) {
            return ContentfulUtil.parseDate(fields."$field.name")
        } else if (field.type.name == "java.util.List") {
            return buildList(field.genericType.actualTypeArguments[0], fields."$field.name")
        } else if (field.type.package?.name == "co.sharptop.church") {
            return buildObject(field.type, fields."$field.name")
        } else {
            return fields."$field.name"
        }
    }

}

