package com.vlatrof.subscriptionsmanager.presentation.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class Parser {

    companion object {

        fun parseLocalDateFromUTCMilliseconds(
            millis: Long, timeZone: ZoneId = ZoneId.systemDefault()
        ): LocalDate {
            return Instant.ofEpochMilli(millis).atZone(timeZone).toLocalDate()
        }

    }

}




