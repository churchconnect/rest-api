package co.sharptop.church

import grails.util.Environment

import org.grails.web.json.JSONObject

import java.util.regex.Matcher

class Entry {

    String id
    Integer version
    Date createdAt
    Date updatedAt

    static void initializeData() {
        if (Environment.current == Environment.DEVELOPMENT) {
            verifyClassList()
        }
    }

    static void verifyClassList() {
        List<Class<Entry>> computedList = []
        new File("src/main/groovy/co/sharptop/church").listFiles().each { File file ->
            Matcher m = ( file.text =~ /(?s).*class\s+(\w+)\s.*extends\s+Entry.*/)
            if (m.matches()) {
                String className = m[0][1]
                Class clazz = Class.forName("co.sharptop.church.$className")
                if (clazz.contentfulContentType) {
                    computedList += clazz
                }
            }
        }
        if (computedList != contentfulDomainClasses) {
            throw new Exception("Entry.contentfulDomainClasses is incorrect. It should look like: $computedList")
        }
    }

    static List<Class<Entry>> contentfulDomainClasses = [
        Asset,
        BannerImage,
        Campus,
        ContentfulPerson,
        Demographic,
        EmailList,
        Event,
        Link,
        MinistryGroup,
        Post,
        PostGroup,
        PrayerRequest,
        Sermon,
        SharingInfo,
        Song,
        SongList,
        WeeklyService
    ]

    static Class getClassForContentType(String contentType) {
        contentfulDomainClasses.find { Class clazz ->
            clazz.contentfulContentType == contentType
        }
    }

    static JSONObject preprocessJSON(JSONObject jsonObject) {
        jsonObject
    }

    static String getContentType() {
        null
    }

    /**
     * Converts a JSON response body for a single entity into the expected Groovy object.
     * This does not do any link resolution -- if there are sub-entities on the object, they will be ignored.
     * If you have a response that needs link resolution, use the ContentfulResponseEntity.
     * One element of complexity handled by this code is to break up internationalized items from the Content Management API
     * for which the value of each field is actually a JSON object containing keys for each language for which that field
     * has a translation. For basic usage, we trust that 'en-US' is the only language in use.
     * @param clazz
     * @param jsonObject
     */
    //TODO: maybe this should all go over to the CMRE. (and create the CMRE in the contentfulService).
    static Entry create(Class<Entry> clazz, JSONObject jsonObject) {
        create(clazz, new ContentManagementResponseEntity(jsonObject))
    }

    static Entry create(Class<Entry> clazz, ContentManagementResponseEntity contentManagementResponseEntity) {
        Map fields = ClassUtil.getTrueFields(clazz, 1).collectEntries {
            [ (it.name): contentManagementResponseEntity.resolveProperty(it.name, it.type) ]
        }

        return clazz.newInstance(fields)
    }

    /**
     * Converts our entry class into the format expected by the Content Management API.
     * @param clazz
     * @param entry
     * @return
     */
    Map toContentManagementFormat() {
        Map fields = ClassUtil.getTrueFields(this.class, 1).findAll {
            this."$it.name" != null
        }.collectEntries {
            [ (it.name): [ "en-US": this."$it.name" ] ]
        }

        return [ fields: fields ]
    }

}
