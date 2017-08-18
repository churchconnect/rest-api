package co.sharptop.church

import java.security.MessageDigest

class HashUtil {
    static String generateMD5(String s) {
        MessageDigest.getInstance("MD5").digest(s.bytes).encodeHex().toString()
    }
}
