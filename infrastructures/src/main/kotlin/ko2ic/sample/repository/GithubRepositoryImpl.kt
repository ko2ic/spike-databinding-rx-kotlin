package ko2ic.sample.repository

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ko2ic.sample.common.Logger
import ko2ic.sample.common.repository.TransactionTemplateForObjectbox
import ko2ic.sample.common.repository.http.HttpClient
import ko2ic.sample.common.repository.local.TransactionTemplateImpl
import ko2ic.sample.model.PermissionDtoForObjectbox
import ko2ic.sample.model.entity.Repo
import ko2ic.sample.model.entity.RepoForObjectbox
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.model.entity.SearchResultForObjectbox
import ko2ic.sample.repository.http.GitHubHttpClient
import ko2ic.sample.repository.local.GitHubLocalStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(val store: GitHubLocalStore, val client: HttpClient, private val tx: TransactionTemplateForObjectbox) : GitHubRepository {

    override var isDirty: Boolean = false

    override fun fetchRepos(query: String, page: Int) = when {
        isDirty -> {
            this.fetchRemote(query, page)
        }
        else -> {
            store.findRepos(query, page).onErrorResumeNext {
                this.fetchRemote(query, page)
            }.subscribeOn(Schedulers.io())
        }
    }

    private fun fetchRemote(query: String, page: Int) = client.call<SearchResult, GitHubHttpClient> { client ->
        client.fetchRepos(query, page).doOnSuccess { result ->
            refreshCache(query, result)
        }
    }

    private fun refreshCache(query: String, result: SearchResult) {
        TransactionTemplateImpl().sync { database ->
            database.deleteAll(Repo::class.java)
            database.deleteAll(SearchResult::class.java)
            result.query = query
            database.save<SearchResult>(result)
        }
        isDirty = false
    }

    override fun fetchReposForObjectbox(query: String, page: Int): Single<SearchResultForObjectbox> = when {
        isDirty -> {
            this.fetchRemoteForObjectbox(query, page)
        }
        else -> {
            store.findReposForObjectbox(query, page).onErrorResumeNext {
                this.fetchRemoteForObjectbox(query, page)
            }.subscribeOn(Schedulers.io())
        }
    }

    private fun fetchRemoteForObjectbox(query: String, page: Int): Single<SearchResultForObjectbox> {
        return client.call<SearchResultForObjectbox, GitHubHttpClient> { client ->
            client.fetchReposForObjectbox(query, page).doOnSuccess { result ->
                refreshCacheForObjectbox(query, result)
            }

        }
    }

    private fun refreshCacheForObjectbox(query: String, result: SearchResultForObjectbox) {
        try {
            tx.sync { database ->
                database.deleteAll(SearchResultForObjectbox::class.java)
                database.deleteAll(RepoForObjectbox::class.java)
                database.deleteAll(PermissionDtoForObjectbox::class.java)
                result.query = query
                // TODO うまくauto-incrementsが動いてくれないので@Id(assignable = true)にして自分で設定している
                result.many.addAll(result.items.mapIndexed { index, entity ->
                    entity.apply {
                        id = index.toLong()
                    }
                })
                database.save(result)
            }

        } catch (e: Exception) {
            Logger.e(e)
        }

        isDirty = false
    }
}