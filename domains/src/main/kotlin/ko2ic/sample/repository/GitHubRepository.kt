package ko2ic.sample.repository

import io.reactivex.Single
import ko2ic.sample.model.entity.SearchResult


interface GitHubRepository {

    var isDirty: Boolean

    fun fetchRepos(query: String, page: Int): Single<SearchResult>
}