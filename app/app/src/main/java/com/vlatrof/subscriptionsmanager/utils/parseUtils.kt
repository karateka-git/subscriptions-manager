package com.vlatrof.subscriptionsmanager.utils

import android.content.Context
import org.xmlpull.v1.XmlPullParser
import java.lang.NullPointerException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun parseXmlResourceMap(context: Context, mapResId: Int): LinkedHashMap<String, String> {

    val parser = context.resources.getXml(mapResId)

    val map = LinkedHashMap<String, String>()
    var key = ""

    var eventType = parser.eventType

    while (eventType != XmlPullParser.END_DOCUMENT) {

        if (eventType == XmlPullParser.START_TAG) {

            if (parser.name == "entry") {
                key = parser.getAttributeValue(null, "key")
                if (key == null) {
                    parser.close();
                    throw NullPointerException("Null item key")
                }
            }

        }

        if (eventType == XmlPullParser.TEXT) {
            map[key] = parser.text
        }

        eventType = parser.next()

    }

    return map

}

fun parseLocalDateFromUTCMilliseconds(millis: Long, zone: ZoneId = ZoneId.systemDefault()): LocalDate {
    return Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()
}