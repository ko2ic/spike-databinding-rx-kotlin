package ko2ic.sample.model

import io.reactivex.Single
import io.reactivex.functions.Consumer
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.repository.GitHubRepository
import ko2ic.sample.test.TestUtils
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class GitHubTest {

    @Rule
    @JvmField
    var rule = MockitoJUnit.rule()

    @Mock
    lateinit var mock: GitHubRepository

    @InjectMocks
    lateinit var target: GitHub

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchRepos_when_取得に成功した() {
        Mockito.`when`(mock.fetchRepos("query", 1)).thenReturn(Single.just(SearchResult().apply {
            query = "query"
            totalCount = 100
        }))

        val testObserver = target.fetchRepos("query", 1).test()
        testObserver.awaitTerminalEvent()

        testObserver.assertNoErrors().assertValue(TestUtils.check<SearchResult>(Consumer<SearchResult> { t ->
            org.junit.Assert.assertThat("query", t.query, org.hamcrest.CoreMatchers.`is`("query"))
            org.junit.Assert.assertThat("totalCount", t.totalCount, org.hamcrest.CoreMatchers.`is`(100))
        }))

        Mockito.verify(mock).fetchRepos("query", 1)
    }

    @Test
    fun fetchRepos_when_取得に失敗した() {
        // TODO: 未実装
//        Mockito.`when`(mock.fetchRepos("query", 1)).thenReturn(Single.error(LocalNotFoundException()))
//
//        val testObserver = target.fetchRepos("query", 1).test()
//
//        testObserver.awaitTerminalEvent()
//        testObserver.assertError(LocalNotFoundException::class.java)
//
//        Mockito.verify(mock).fetchRepos("query", 1)
    }


}