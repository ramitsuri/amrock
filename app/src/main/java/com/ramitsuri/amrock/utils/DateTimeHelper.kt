package com.ramitsuri.amrock.utils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeHelper(private val zoneId: ZoneId, private val locale: Locale) {
    fun parse(dateTime: String): Instant {
        return try {
            Instant.parse(dateTime)
        } catch (e: Exception) {
            Instant.now()
        }
    }

    fun format(instant: Instant, format: String): String {
        val formatter = DateTimeFormatter.ofPattern(format, locale)
        val dateTime = ZonedDateTime.ofInstant(instant, zoneId)
        return formatter.format(dateTime)
    }
}