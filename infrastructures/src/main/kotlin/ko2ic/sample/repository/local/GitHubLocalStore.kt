package ko2ic.sample.repository.local

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ko2ic.sample.model.entity.SearchResult

class GitHubLocalStore {

    fun findRepos(query: String, page: Int): Single<SearchResult> {
        return Single.defer(
                {
                    // TODO: roomの処理
                    Single.just(SearchResult())
                }).subscribeOn(Schedulers.io())
    }
}
