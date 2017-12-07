package ko2ic.sample.common.model.valueobject.enums


sealed class HttpErrorType {

    class None() : HttpErrorType()
    data class Unknown(val t: Throwable) : HttpErrorType()

    class TimedoutError(val t: Throwable) : HttpErrorType() // タイムアウト SocketTimeoutException
    class CannotFindHost(val t: Throwable) : HttpErrorType() // ホスト名を解決できない場合 UnknownHostException,NoRouteToHostException
    class CannotConnectToHost(val t: Throwable) : HttpErrorType() // ホストに接続できなかった場合（ホストがダウン。Portが受け付けない状態など）ConnectException
    data class StatusCode(val code: Int, val map: Map<String, Any>?, val t: Throwable) : HttpErrorType() {
        fun isBadRequest() = this.code == 400
        fun isUnauthorized() = this.code == 401
        fun isPaymentRequired() = this.code == 402
        fun isForbidden() = this.code == 403
        fun isNotFound() = this.code == 404
        fun isInternalServerError() = this.code == 500
        fun isServiceUnavailable() = this.code == 503
    }
}


