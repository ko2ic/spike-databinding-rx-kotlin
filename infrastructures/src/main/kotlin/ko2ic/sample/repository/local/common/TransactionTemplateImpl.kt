package ko2ic.sample.repository.local.common

import io.realm.Realm
import io.realm.RealmAsyncTask
import io.realm.RealmModel
import ko2ic.sample.repository.Database
import ko2ic.sample.repository.TransactionTemplate

class TransactionTemplateImpl : TransactionTemplate {

    private var tx: RealmAsyncTask? = null

    override fun sync(doProcess: (Database) -> Unit) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                val wrapper: Database = RealmWrapper(realm)
                doProcess(wrapper)
            }
            realm.close()
        }
    }

    override fun <T> async(doProcess: () -> T, success: () -> Unit, error: () -> Unit) where T : RealmModel {
        val realm = Realm.getDefaultInstance()
        tx = realm.executeTransactionAsync(Realm.Transaction {
            val model = doProcess()
            realm.copyToRealmOrUpdate(model)

        }, Realm.Transaction.OnSuccess {
            success()
            realm.close()
        }, Realm.Transaction.OnError {
            error()
            realm.close()
        })
    }

    override fun cancel() {
        val isCancelled = tx?.isCancelled ?: false
        if (isCancelled) {
            tx?.cancel()
        }
    }
}