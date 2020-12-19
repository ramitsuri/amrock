package com.ramitsuri.amrock.serializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.ramitsuri.amrock.utils.DateTimeHelper
import java.lang.reflect.Type
import java.text.ParseException
import java.time.Instant


class InstantAdapter(private val dateTimeHelper: DateTimeHelper) :
    JsonDeserializer<Instant> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Instant {
        try {
            json?.let {
                return dateTimeHelper.parse(json.asString)
            }
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }
        return Instant.now()
    }

}