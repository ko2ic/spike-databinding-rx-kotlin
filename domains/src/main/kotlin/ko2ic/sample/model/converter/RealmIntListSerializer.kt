package jp.ra9.models.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import io.realm.RealmList
import java.lang.reflect.Type


class RealmIntListSerializer : JsonDeserializer<RealmList<RealmInt>> {

    override fun deserialize(element: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): RealmList<RealmInt> {
        val list = RealmList<RealmInt>()
        val jsonArray = element!!.getAsJsonArray()

        jsonArray.forEach { item ->
            list.add(RealmInt().apply { value = item.asInt })
        }
        return list
    }


}