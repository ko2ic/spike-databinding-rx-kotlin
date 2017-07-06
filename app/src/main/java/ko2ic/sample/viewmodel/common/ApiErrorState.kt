package ko2ic.sample.ui.viewmodel.common

import io.reactivex.subjects.PublishSubject
import ko2ic.sample.model.valueobject.enums.HttpErrorType

class ApiErrorState private constructor() {

    val error = PublishSubject.create<HttpErrorType>().toSerialized()

    private object Holder {
        val INSTANCE = ApiErrorState()
    }

    companion object {
        val instance: ApiErrorState by lazy { ApiErrorState.Holder.INSTANCE }
    }


}