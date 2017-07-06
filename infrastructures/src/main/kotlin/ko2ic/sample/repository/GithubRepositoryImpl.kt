package ko2ic.sample.repository

import io.reactivex.schedulers.Schedulers
import ko2ic.sample.common.repository.http.HttpClient
import ko2ic.sample.common.repository.local.TransactionTemplateImpl
import ko2ic.sample.model.entity.Repo
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.repository.http.GitHubHttpClient
import ko2ic.sample.repository.local.GitHubLocalStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(val store: GitHubLocalStore, val client: HttpClient) : GitHubRepository {

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

    private fun fetchRemote(query: String, page: Int) = client.call <SearchResult, GitHubHttpClient> { client ->
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
}