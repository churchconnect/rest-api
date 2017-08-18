/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import java.text.SimpleDateFormat

class PostGroup extends Entry {

    String title
    Asset media
    List<Post> posts
    String rssUrl
    List<Post> publishedPosts

    static String contentfulContentType = "post-group"

    List<Post> getPublishedPosts() {
        rssPosts + contentfulPosts
    }

    List<Post> getContentfulPosts() {
        posts.findAll {
            it && (!it.date || it.date < new Date())
        }
    }

    List<Post> getRssPosts() {
        (rssUrl) ? createRssPosts(rssUrl) : []
    }

    List<Post> createRssPosts(String rssUrl) {
        def rootRss = new XmlSlurper().parse(rssUrl)

        rootRss.channel.item.withIndex().collect { item, index ->
            /**
             * MD5 Id generated with a Seed word that WONT change. Hence same MD5 on each request
             */
            new Post(
                    id: HashUtil.generateMD5("rssPost-" + id + "-" + index),
                    title: item.title,
                    summary: "<img src='${item.content.thumbnail.@url}' />",
                    date: new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss Z").parse(item.pubDate.toString()),
                    content: item.description,
                    media: new Asset(
                            title: item.content.title,
                            url: item.content.thumbnail.@url,
                            contentType: "image/webp",
                            width: item.content.thumbnail.@width.toInteger(),
                            height: item.content.thumbnail.@height.toInteger()
                    )
            )
        }
    }
}
