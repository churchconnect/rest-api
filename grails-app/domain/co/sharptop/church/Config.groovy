package co.sharptop.church

class Config {

    String appName
    String appURL

    static constraints = {
    }

    static mapping = {
        appURL column: 'app_url'
    }

    static getCurrent() {
        list().first()
    }

    static initializeData() {
        new Config(
            appName: "Church Connect",
            appURL: "http://localhost:9000"
        ).save(failOnError: true)
    }

}
