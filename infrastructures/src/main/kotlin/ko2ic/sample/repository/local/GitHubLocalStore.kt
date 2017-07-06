package ko2ic.sample.repository.local

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import ko2ic.sample.model.entity.SearchResult
import ko2ic.sample.model.exception.LocalNotFoundException

class GitHubLocalStore {

    fun findRepos(query: String, page: Int): Single<SearchResult> {
        return Single.defer(
                {
                    Realm.getDefaultInstance().use { realm ->
                        val q = realm.where(SearchResult::class.java)
                        val result = q.equalTo("query", query).findFirst() ?: SearchResult()
                        if (result.isManaged) {
                            Single.just(realm.copyFromRealm(result))
                        } else {
                            Single.error(LocalNotFoundException())
                        }
                    }
                }).subscribeOn(Schedulers.io())
    }

    // TODO: どっちがいいか？
//    fun findRepos(query: String, page: Int): Single<SearchResult> {
//        return Single.fromCallable(
//                {
//                    Realm.getDefaultInstance().use { realm ->
//                        val q = realm.where(SearchResult::class.java)
//                        val result = q.equalTo("query", query).findFirst() ?: SearchResult()
//                        if (result.isManaged) {
//                            realm.copyFromRealm(result)
//                        } else {
//                            throw LocalNotFoundException()
//                        }
//                    }
//                }).subscribeOn(Schedulers.io())
//    }

}
