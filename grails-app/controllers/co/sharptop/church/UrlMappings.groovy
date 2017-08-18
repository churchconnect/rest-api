/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

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
