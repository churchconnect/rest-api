/*
 * Copyright (c) 2016 by SharpTop Software, LLC
 * All rights reserved. No part of this software project may be used, reproduced, distributed, or transmitted in any
 * form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior
 * written permission of SharpTop Software, LLC. For permission requests, write to the author at info@sharptop.co.
 */

package co.sharptop.church

class Sermon extends Entry {

    String title
    String content
    Asset image
    Asset video
    Asset audio
    List<ContentfulPerson> speakers
    List<Demographic> demographics
    SharingInfo sharingInfo

    static String contentfulContentType = "sermon"

}
