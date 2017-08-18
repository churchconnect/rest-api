/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import org.grails.web.json.JSONObject

class Asset extends Entry {

    String title
    String url
    String contentType
    //TODO: figure out how to separate image assets from non-image assets.
    Integer width
    Integer height

    static String contentfulContentType = "asset"

    static JSONObject preprocessJSON(JSONObject jsonObject) {
        new JSONObject(
            [
                title: jsonObject.title,
                url: jsonObject.file.url,
                contentType: jsonObject.file.contentType,
                width: jsonObject.file.details?.image?.width,
                height: jsonObject.file.details?.image?.height
            ]
        )
    }

    String getUrl() {
        url.startsWith('//') ? "https:$url" : url
    }

}