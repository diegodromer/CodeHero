package com.diegolima.codehero.data.network.loadthumbnail

import com.diegolima.codehero.constants.ALGORITHM
import com.diegolima.codehero.constants.HEX
import java.lang.Exception
import java.lang.StringBuilder

fun String.md5(): String {
    try {
        val digester = java.security.MessageDigest.getInstance(ALGORITHM)
        digester.update(toByteArray())
        val msgDigest = digester.digest()
        val hexStr = StringBuilder()
        for (isMessageDigest in msgDigest) {
            var hex = Integer.toHexString(HEX and isMessageDigest.toInt())
            while (hex.length < 2)
                hex = "0$hex"
            hexStr.append(hex)
        }
        return hexStr.toString()

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}