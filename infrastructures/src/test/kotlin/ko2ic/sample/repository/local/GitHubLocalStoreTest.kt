package ko2ic.sample.repository.local

import ko2ic.sample.test.AbstractObjectBoxTest
import ko2ic.sample.test.RxImmediateSchedulerRule
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GitHubLocalStoreTest : AbstractObjectBoxTest() {

    // https://github.com/powermock/powermock/issues/687
    // TODO: 本当は、@ClassRuleにしたいが、PowerMockRunnerを利用すると動かないバグがある
    @Rule
    @JvmField
    val schedulers: RxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Before
    override fun setUp() {
        super.setUp()
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun findRepos() {
        // TODO: realmがandroidに依存しているので動作しない。また、roboretricが動作してもまだrealmがサポートされていない

//        val target = GitHubLocalStore()
//        val result = target.find("query", 1)
//
//        Assert.assertNull(result)
    }


    @Test
    fun findReposForObjectbox() {
        // TODO: macでの単体テストをサポートしていないようだ.https://github.com/objectbox/objectbox-java/issues/19
//        val target = GitHubLocalStore(store)
//        val testSubscriber = target.findReposForObjectbox("ko2ic", 1).test()
//        testSubscriber.awaitTerminalEvent()
//
//        testSubscriber.assertNoErrors().assertValue(TestUtils.check<SearchResultForObjectbox>(Consumer<SearchResultForObjectbox> { t ->
//            Assert.assertThat("query", t.query, CoreMatchers.`is`("ko2ic"))
//        }))
    }
}