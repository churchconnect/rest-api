package co.sharptop.church

import org.grails.web.json.JSONObject

class WeeklyService extends Entry {

    String title
    JSONObject location
    String day
    String time

    static String contentfulContentType = "weeklyService"

}
