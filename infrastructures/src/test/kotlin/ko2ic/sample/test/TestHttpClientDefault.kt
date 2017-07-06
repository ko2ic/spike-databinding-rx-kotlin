package ko2ic.sample.test

import ko2ic.sample.repository.http.common.HttpClientDefault
import okhttp3.HttpUrl


class TestHttpClientDefault(url: HttpUrl) : HttpClientDefault() {

    override val baseUrl: String = url.toString()
}