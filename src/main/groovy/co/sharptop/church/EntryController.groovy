/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

import grails.artefact.Artefact
import grails.converters.JSON

@Artefact("Controller")
class EntryController<T extends Entry> implements ResourceNotFoundHandlerTrait {

    Class<T> resource
    ContentfulService contentfulService

    EntryController(Class<T> resource) {
        this.resource = resource
    }

    def index() {
        List<Entry> entries = contentfulService.fetchEntries(resource)

        if (!entries) {
            throw new ResourceNotFoundException()
        }

        render (entries as JSON)
    }

    def show(String id) {
        Entry entry = contentfulService.fetchEntry(resource, id)

        if (!entry) {
            throw new ResourceNotFoundException()
        }

        render (entry as JSON)
    }

}
