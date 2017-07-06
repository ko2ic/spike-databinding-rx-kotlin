package ko2ic.sample.ui.viewmodel

import ko2ic.sample.model.exception.HttpErrorTypeException
import ko2ic.sample.model.valueobject.enums.HttpErrorType
import ko2ic.sample.ui.viewmodel.common.ApiErrorState

interface ViewModel {

    fun error(t: Throwable) {
        if (t is HttpErrorTypeException) {
            ApiErrorState.instance.error.onNext(t.errorType)
        }
        ApiErrorState.instance.error.onNext(HttpErrorType.Unknown(t))
    }
}