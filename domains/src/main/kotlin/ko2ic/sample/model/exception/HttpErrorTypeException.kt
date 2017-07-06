package ko2ic.sample.model.exception

import ko2ic.sample.model.valueobject.enums.HttpErrorType


class HttpErrorTypeException(val errorType: HttpErrorType) : Exception() {
}