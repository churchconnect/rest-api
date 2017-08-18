/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value

@Transactional
class EmailService {

    @Value('${grails.mail.disabled:false}')
    Boolean disabled

    /**
     * sends an email message
     * if mail is disabled, logs the message parameters.
     * @param msg - a map containing the address, the subject, the view, and any other data needed by the view
     * @return
     */
    def send(Map msg) {
        if (disabled) {
            log.warn("Email disabled. Not sending email message $msg")
            return
        }

        log.debug("Sending email message $msg")
        sendMail {
            async true
            to msg.address
            subject msg.subject
            if (msg.text) {
                body msg.text
            }
            if (msg.html) {
                html msg.html
            }
        }
    }

}
