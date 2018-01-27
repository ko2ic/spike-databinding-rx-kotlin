package ko2ic.sample.common.repository.local

import io.objectbox.BoxStore
import io.objectbox.Property
import io.objectbox.query.Query
import ko2ic.sample.common.repository.DatabaseForObjectbox

class BoxStoreWrapper(private val database: BoxStore) : DatabaseForObjectbox {

    override fun <T : Any> save(model: T) {
        database.boxFor(model.javaClass).put(model)
    }

    override fun <T> deleteAll(clazz: Class<T>) {
        database.boxFor(clazz).removeAll()
    }

    override fun <T> deleteByValue(value: Pair<Property, String>, clazz: Class<T>) {
        val list = database.boxFor(clazz).find(value.first, value.second)
        database.boxFor(clazz).remove(list)
    }

    override fun <T> findByValue(value: Pair<Property, String>, clazz: Class<T>): List<T> {
        return database.boxFor(clazz).find(value.first, value.second)
    }


    override fun <T> query(clazz: Class<T>): Query<T> {
        return database.boxFor(clazz).query().build()
    }

}