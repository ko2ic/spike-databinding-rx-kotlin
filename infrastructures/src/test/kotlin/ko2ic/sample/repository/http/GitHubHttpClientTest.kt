package ko2ic.sample.repository.http

import io.reactivex.functions.Consumer
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.test.TestHttpClientDefault
import ko2ic.sample.test.TestUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Test

class GitHubHttpClientTest {

    private var server = MockWebServer()

    @Throws(Exception::class)
    fun setUp() {
        server.start()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun fetchRepos() {

        val response = MockResponse()
                .addHeader("Content-Type", "application/json")
                .setResponseCode(200)
                .setBody(
                        """
{"total_count":2,"incomplete_results":false,
    "items":[
                {"id":1,"name":"test1","full_name":"test/Test1", "description": "概要だよ1", "stars":1},
                {"id":2,"name":"test2","full_name":"test/Test2", "description": "概要だよ2", "stars":2}
            ]
}
"""
                )

        server.enqueue(response)

        val client = TestHttpClientDefault(server.url("/")).lookup(GitHubHttpClient::class.java)

        val testSubscriber = client.fetchRepos("query", 1).test()
        testSubscriber.awaitTerminalEvent()

        testSubscriber.assertNoErrors().assertValue(TestUtils.check<SearchResult>(Consumer<SearchResult> { t ->
            Assert.assertThat("totalCount", t.totalCount, CoreMatchers.`is`(2))
            Assert.assertThat("isIncompleteResults", t.isIncompleteResults, CoreMatchers.`is`(false))
            Assert.assertThat("items[0].id", t.items[0].id, CoreMatchers.`is`(1))
            Assert.assertThat("items[0].name", t.items[1].name, CoreMatchers.`is`("test2"))
        }))

        val request = server.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/search/repositories?q=query&page=1"))
    }
}
