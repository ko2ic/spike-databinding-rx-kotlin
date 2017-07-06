package ko2ic.sample.repository.http.common

import ko2ic.sample.common.repository.http.HttpClientBase
import okhttp3.OkHttpClient

open class HttpClientDefault : HttpClientBase() {

    override val baseUrl: String = "https://api.github.com"

    override fun addInterceptor(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.addInterceptor(RequestHeaderInterceptor())
        return builder
    }
}