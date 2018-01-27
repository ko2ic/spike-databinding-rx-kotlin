package ko2ic.sample.common.repository

interface TransactionTemplateForObjectbox {

    fun sync(doProcess: (DatabaseForObjectbox) -> Unit)
    fun <T> async(doProcess: () -> T, success: () -> Unit, error: () -> Unit)
}