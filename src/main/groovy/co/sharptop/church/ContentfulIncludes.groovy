/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import org.grails.web.json.JSONObject

class ContentfulIncludes {

    JSONObject includes

    ContentfulIncludes(JSONObject includesJSON) {
        includes = includesJSON
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
        fields?.put("id", id)
        return fields
    }

    JSONObject getAsset(String id) {
        JSONObject fields = includes.Asset?.find { it.sys.id == id }?.fields
        fields?.put("id", id)
        return fields
    }

}
