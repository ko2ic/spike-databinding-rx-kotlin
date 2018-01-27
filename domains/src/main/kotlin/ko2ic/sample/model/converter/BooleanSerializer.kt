package jp.ra9.models.converter

import com.google.gson.*
import java.lang.reflect.Type


class BooleanSerializer : JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

    override fun serialize(value: Boolean?, type: Type, context: JsonSerializationContext): JsonElement {
        if (value == null) {
            return JsonPrimitive(false)
        } else {
            return JsonPrimitive(if (value) 1 else 0)
        }
    }

    override fun deserialize(element: JsonElement, type: Type, context: JsonDeserializationContext): Boolean? {
        try {
            return element.asInt == 1
        } catch (e: NumberFormatException) {
            return element.asBoolean
        }
    }

}
