package ko2ic.sample.repository

import io.reactivex.Single
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.model.entity.SearchResultForObjectbox


interface GitHubRepository {

    var isDirty: Boolean

    fun fetchRepos(query: String, page: Int): Single<SearchResult>

    fun fetchReposForObjectbox(query: String, page: Int): Single<SearchResultForObjectbox>
}