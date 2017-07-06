package ko2ic.sample.common.repository

import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults


interface Database {

    fun <T> save(model: T) where T : RealmModel
    fun <T> deleteAll(clazz: Class<T>) where T : RealmModel
    fun <T> deleteByValue(value: Pair<String, String>, clazz: Class<T>) where T : RealmModel

    fun <T> findByValue(value: Pair<String, String>, clazz: Class<T>): RealmResults<T>? where T : RealmModel

    fun <T> query(clazz: Class<T>): RealmQuery<T> where T : RealmModel

}