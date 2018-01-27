package jp.ra9.models.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.realm.RealmList
import java.lang.reflect.Type


class RealmStringListSerializer : JsonDeserializer<RealmList<RealmString>> {

    override fun deserialize(element: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): RealmList<RealmString> {
        val list = RealmList<RealmString>()
        val jsonArray = element!!.getAsJsonArray()

        jsonArray.forEach { item ->
            list.add(RealmString().apply { value = item.asString })
        }
        return list
    }


}