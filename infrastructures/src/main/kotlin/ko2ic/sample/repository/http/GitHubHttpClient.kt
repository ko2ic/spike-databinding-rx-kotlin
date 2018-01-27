package ko2ic.sample.repository.http

import io.reactivex.Single
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.model.entity.SearchResultForObjectbox
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubHttpClient {

    @GET("search/repositories")
    fun fetchRepos(@Query("q") query: String, @Query("page") page: Int): Single<SearchResult>

    @GET("search/repositories")
    fun fetchReposForObjectbox(@Query("q") query: String, @Query("page") page: Int): Single<SearchResultForObjectbox>

}