package ko2ic.sample.common.model.exception

import ko2ic.sample.common.model.valueobject.enums.HttpErrorType


class HttpErrorTypeException(val errorType: HttpErrorType) : Exception() {
}