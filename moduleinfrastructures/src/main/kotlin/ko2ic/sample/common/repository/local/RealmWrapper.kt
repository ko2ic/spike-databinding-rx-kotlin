package ko2ic.sample.common.repository.local

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.RealmResults
import ko2ic.sample.common.repository.Database

class RealmWrapper(val database: Realm) : Database {

    override fun <T> save(model: T) where T : RealmModel {
        database.copyToRealmOrUpdate(model)
    }

    override fun <T> deleteAll(clazz: Class<T>) where T : RealmModel {
        database.where(clazz).findAll().deleteAllFromRealm()
    }

    override fun <T> deleteByValue(value: Pair<String, String>, clazz: Class<T>) where T : RealmModel {
        this.findByValue(value, clazz)?.deleteAllFromRealm()
    }

    override fun <T> findByValue(value: Pair<String, String>, clazz: Class<T>): RealmResults<T>? where T : RealmModel
            = database.where(clazz).equalTo(value.first, value.second).findAll()


    override fun <T> query(clazz: Class<T>): RealmQuery<T> where T : RealmModel
            = database.where(clazz)

}