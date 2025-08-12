package com.proyecto.proyectoesfrt.adaptador

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {

    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")  // Cambiar a formato completo de hora

    override fun serialize(src: LocalTime, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(src.format(formatter))
    }

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalTime {
        return LocalTime.parse(json.asString, formatter) // Parsear usando el nuevo formato
    }
}