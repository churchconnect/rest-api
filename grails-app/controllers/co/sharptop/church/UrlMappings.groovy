package co.sharptop.church

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{}

        "/${controller}s" {
            action = [GET: "index", POST: "save"]
            format = "json"
        }

        "/${controller}s/$id" {
            action = [GET: "show", PUT: "update", DELETE: "delete"]
            format = "json"
        }

        "/me" (controller: 'person', action: 'getLoggedInUser')
        "/metadata"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
