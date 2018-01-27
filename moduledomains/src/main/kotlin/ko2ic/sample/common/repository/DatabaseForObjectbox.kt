package ko2ic.sample.common.repository

import io.objectbox.Property
import io.objectbox.query.Query


interface DatabaseForObjectbox {

    fun <T : Any> save(model: T)

    fun <T> deleteAll(clazz: Class<T>)
    fun <T> deleteByValue(value: Pair<Property, String>, clazz: Class<T>)

    fun <T> findByValue(value: Pair<Property, String>, clazz: Class<T>): List<T>

    fun <T> query(clazz: Class<T>): Query<T>


}