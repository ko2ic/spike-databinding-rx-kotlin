package ko2ic.sample.common.repository.local

import io.objectbox.BoxStore
import ko2ic.sample.common.repository.DatabaseForObjectbox
import ko2ic.sample.common.repository.TransactionTemplateForObjectbox
import javax.inject.Inject

class TransactionTemplateForObjectboxImpl @Inject constructor(private val boxStore: BoxStore) : TransactionTemplateForObjectbox {

    override fun sync(doProcess: (DatabaseForObjectbox) -> Unit) {
        boxStore.runInTx {
            val wrapper: DatabaseForObjectbox = BoxStoreWrapper(boxStore)
            doProcess(wrapper)
        }
    }

    override fun <T> async(doProcess: () -> T, success: () -> Unit, error: () -> Unit) {
        boxStore.callInTxAsync({
            doProcess()
        }, { result, e: Throwable? ->
            if (e == null) {
                success()
            } else {
                error()
            }
        })
    }
}