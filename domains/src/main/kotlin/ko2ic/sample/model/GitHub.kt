package ko2ic.sample.model

import android.util.Log
import io.reactivex.Single
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.repository.GitHubRepository
import javax.inject.Inject

class GitHub @Inject constructor(val repository: GitHubRepository) {

    fun fetchRepos(query: String, page: Int): Single<SearchResult> = repository.run {
        isDirty = true
        fetchRepos(query, page).doOnError { e ->
            Log.d("GitHub", e.message, e)
        }
    }
}