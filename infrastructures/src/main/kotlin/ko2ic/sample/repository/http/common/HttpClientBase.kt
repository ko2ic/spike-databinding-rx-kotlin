package ko2ic.sample.repository.http.common

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class HttpClientBase {

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

    fun <T> lookup(clazz: Class<T>): T = retrofit.create(clazz)

    abstract val baseUrl: String
    // 継承先で処理を入れたい時にoverrideする
    open fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder = builder
}