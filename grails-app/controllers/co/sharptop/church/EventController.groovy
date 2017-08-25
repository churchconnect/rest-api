/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class EventController extends EntryController {

    EventService eventService

    EventController() {
        super(Event)
    }

    def index() {
        List<Entry> entries = eventService.getAllEvents()

        if (!entries) {
            throw new ResourceNotFoundException()
        }

        render (entries as JSON)
    }

    def show(String id) {
        Entry entry = eventService.getEvent(id)

        if (!entry) {
            throw new ResourceNotFoundException()
        }

        render (entry as JSON)
    }
}