package ko2ic.sample.repository

import io.realm.RealmModel

interface TransactionTemplate {

    fun sync(doProcess: (Database) -> Unit)

    fun <T> async(doProcess: () -> T, success: () -> Unit, error: () -> Unit) where T : RealmModel
    fun cancel()
}