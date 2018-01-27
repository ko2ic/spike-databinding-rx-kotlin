package jp.ra9.models.converter

import com.google.gson.*
import io.realm.RealmList
import io.realm.RealmModel
import java.lang.reflect.Type


class RealmListSerializer<T>(private val clazz: Class<T>) : JsonSerializer<RealmList<T>>,
        JsonDeserializer<RealmList<T>> where T : RealmModel {

    override fun deserialize(element: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): RealmList<T> {
        val list = RealmList<T>()
        val jsonArray = element!!.getAsJsonArray()
        for (item in jsonArray) {
            list.add(context!!.deserialize<T>(item, clazz) as T)
        }
        return list
    }

    override fun serialize(src: RealmList<T>?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val jsonArray = JsonArray()
        for (entity in src!!) {
            jsonArray.add(context!!.serialize(entity))
        }
        return jsonArray
    }


}