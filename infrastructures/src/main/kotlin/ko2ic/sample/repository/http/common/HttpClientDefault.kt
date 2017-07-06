package ko2ic.sample.repository.http.common

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ko2ic.sample.model.exception.HttpErrorTypeException
import ko2ic.sample.model.valueobject.enums.HttpErrorType
import okhttp3.OkHttpClient
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class HttpClientDefault : HttpClientBase() {

    override val baseUrl: String = "https://api.github.com"

    override fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(RequestHeaderInterceptor())
        return builder
    }

    companion object {
        fun asHttpErrorTypeException(throwable: Throwable) = when (throwable) {
            is HttpException -> {
                val gson = Gson()
                val listType = object : TypeToken<Map<String, Any>>() {

                }.type

                val excption = throwable
                var json: String? = null
                try {
                    json = excption.response().errorBody().string()
                    val map = gson.fromJson<Map<String, Any>>(json, listType)

                    HttpErrorTypeException(HttpErrorType.StatusCode(excption.code(), map))
                } catch (e: IOException) {
                    HttpErrorTypeException(HttpErrorType.Unknown(throwable))
                }
            }
            is SocketTimeoutException -> {
                HttpErrorTypeException(HttpErrorType.TimedoutError())
            }
            is UnknownHostException, is NoRouteToHostException -> {
                HttpErrorTypeException(HttpErrorType.CannotFindHost())
            }
            is ConnectException -> {
                HttpErrorTypeException(HttpErrorType.CannotConnectToHost())
            }
            else -> {
                HttpErrorTypeException(HttpErrorType.Unknown(throwable))
            }
        }
    }
}