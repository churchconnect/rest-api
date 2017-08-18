/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

class ContentfulUtil {

    /**
     * parses the date fields that are returned by the ContentFul API
     * according to the format they use for their dates.
     * @param date
     * @return
     */
    static Date parseDate(String date) {
        if (!date) {
            return null
        }

        if (date.size() > 16) {
            return Date.parse("yyyy-MM-dd'T'HH:mmXXX", date)
        }

        Date.parse("yyyy-MM-dd'T'HH:mm", date)
    }

    /**
     * parses date fields that come from sys objects, which are much more complex than the date field values.
     * @param date
     * @return
     */
    static Date parseSystemDate(String date) {
        if (!date) {
            return null
        }

        Date.parse("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", date)
    }

}
