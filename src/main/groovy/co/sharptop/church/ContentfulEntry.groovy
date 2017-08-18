/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

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
