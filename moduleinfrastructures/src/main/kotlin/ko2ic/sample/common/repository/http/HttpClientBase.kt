package ko2ic.sample.common.repository.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ko2ic.sample.common.model.exception.HttpErrorTypeException
import ko2ic.sample.common.model.valueobject.enums.HttpErrorType
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class HttpClientBase : HttpClientLocator {

    private val retrofit by lazy {
        val httpClient = OkHttpClientSingleton.instance.httpClient
        val builder = this.addInterceptor(httpClient.newBuilder())

        Retrofit.Builder()
                .baseUrl(this.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .client(builder.build())
                .build()
    }

    override fun <T> lookup(clazz: Class<T>): T = retrofit.create(clazz)

    abstract val baseUrl: String
    // 継承先で処理を入れたい時にoverrideする
    open fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder = builder

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

                    HttpErrorTypeException(HttpErrorType.StatusCode(excption.code(), map, throwable))
                } catch (e: IOException) {
                    HttpErrorTypeException(HttpErrorType.Unknown(throwable))
                }
            }
            is SocketTimeoutException -> {
                HttpErrorTypeException(HttpErrorType.TimedoutError(throwable))
            }
            is UnknownHostException, is NoRouteToHostException -> {
                HttpErrorTypeException(HttpErrorType.CannotFindHost(throwable))
            }
            is ConnectException -> {
                HttpErrorTypeException(HttpErrorType.CannotConnectToHost(throwable))
            }
            else -> {
                HttpErrorTypeException(HttpErrorType.Unknown(throwable))
            }
        }
    }

}