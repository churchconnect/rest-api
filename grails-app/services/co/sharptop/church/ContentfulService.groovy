package co.sharptop.church

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus

class ContentfulService {

    @Value('${church.contentful.read.apiURL}')
    String readApiURL

    @Value('${church.contentful.read.apiKey}')
    String readApiKey

    @Value('${church.contentful.write.apiURL}')
    String writeApiURL

    @Value('${church.contentful.write.apiKey}')
    String writeApiKey

    RestBuilder rest = new RestBuilder()

    List fetchEntries(Class<Entry> clazz) {
        clazz.contentfulContentType ? fetchEntries(clazz.contentfulContentType) : null
    }

    List fetchEntries(String contentType) {
        RestResponse response = rest.get("$readApiURL/entries?content_type=$contentType&include=10") {
            header "Authorization", "Bearer $readApiKey"
        }

        return new ContentfulResponseEntity(response.json).buildObjects()
    }

    Entry fetchEntry(Class<Entry> clazz, String id) {
        fetchEntries(clazz).find { it.id == id }
    }

    Asset fetchAsset(String id) {
        RestResponse response = rest.get("$readApiURL/assets/$id") {
            header "Authorization", "Bearer $readApiKey"
        }

        return new Asset(Asset.preprocessJSON(response.json.fields) + [
                id: response.json.sys.id,
                createdAt: ContentfulUtil.parseSystemDate(response.json.sys.createdAt),
                updatedAt: ContentfulUtil.parseSystemDate(response.json.sys.updatedAt),
                version: response.json.sys.revision
        ])
    }

    Link fetchLink(String id) {
        fetchEntry(Link, id)
    }

    List<BannerImage> fetchBannerImages() {
        fetchEntries(BannerImage)
    }

    List<Event> fetchEvents() {
        fetchEntries(Event)
    }

    List<MinistryGroup> fetchMinistryGroups() {
        fetchEntries(MinistryGroup)
    }

    List<PostGroup> fetchPostGroups() {
        fetchEntries(PostGroup)
    }

    List<PrayerRequest> fetchPrayerRequests() {
        fetchEntries(PrayerRequest)
    }

    List<Sermon> fetchSermons() {
        fetchEntries(Sermon)
    }

    List<SongList> fetchSongLists() {
        fetchEntries(SongList)
    }

    Entry createEntry(Class<Entry> clazz, Entry entry) {
        RestResponse response = rest.post("$writeApiURL/entries") {
            header "Authorization", "Bearer $writeApiKey"
            header "Content-Type", "application/vnd.contentful.management.v1+json"
            header "X-Contentful-Content-Type", clazz.contentfulContentType
            json entry.toContentManagementFormat()
        }

        if (response.statusCode != HttpStatus.CREATED) {
            throw new ContentfulCreateEntryFailureException(response.json.toString())
        }

        Entry result = Entry.create(clazz, response.json)

        publish(result)

        return result
    }

    void publish(Entry entry) {
        RestResponse response = rest.put("$writeApiURL/entries/$entry.id/published") {
            header "Authorization", "Bearer $writeApiKey"
            header "X-Contentful-Version", "$entry.version"
        }

        if (response.statusCode != HttpStatus.OK) {
            throw new ContentfulPublishFailureException(response.json.toString())
        }
    }

    PrayerRequest createPrayerRequest(PrayerRequest prayerRequest) {
        createEntry(PrayerRequest, prayerRequest)
    }

}

