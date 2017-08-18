/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

class Config {

    String appName
    String appURL
    String liveStreamLinkId

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
            appName: "TRBC Connect",
            appURL: "http://localhost:9000",
            liveStreamLinkId: "4DCKfXk6W4IakA42AyUyGy"
        ).save(failOnError: true)
    }

}
