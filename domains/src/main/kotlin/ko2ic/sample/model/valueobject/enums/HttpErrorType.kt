package ko2ic.sample.model.valueobject.enums


sealed class HttpErrorType {

    class None() : HttpErrorType()
    data class Unknown(val t: Throwable) : HttpErrorType()
    class TimedoutError() : HttpErrorType() // タイムアウト SocketTimeoutException
    class CannotFindHost() : HttpErrorType() // ホスト名を解決できない場合 UnknownHostException,NoRouteToHostException
    class CannotConnectToHost() : HttpErrorType() // ホストに接続できなかった場合（ホストがダウン。Portが受け付けない状態など）ConnectException
    data class StatusCode(val code: Int, val map: Map<String, Any>?) : HttpErrorType() {
        fun isUnprocessableEntity() = this.code == 422
    }

}

