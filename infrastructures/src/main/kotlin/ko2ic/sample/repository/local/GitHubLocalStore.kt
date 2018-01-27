package ko2ic.sample.repository.local

import io.objectbox.BoxStore
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import ko2ic.sample.common.model.exception.LocalNotFoundException
import ko2ic.sample.model.entity.*
import javax.inject.Inject

class GitHubLocalStore @Inject constructor(private val boxStore: BoxStore) {


    fun findRepos(query: String, page: Int): Single<SearchResult> {
        return Single.defer(
                {
                    Realm.getDefaultInstance().use { realm ->
                        val q = realm.where(SearchResult::class.java)
                        val result = q.equalTo("query", query).findFirst()
                        if (result != null) {
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


    fun findReposForObjectbox(query: String, page: Int): Single<SearchResultForObjectbox> {
        return Single.defer(
                {
                    val q = boxStore.boxFor(SearchResultForObjectbox::class.java).query().equal(SearchResultForObjectbox_.query, query).build()
                    val result = q.findFirst()
                    if (result != null) {
                        val list = boxStore.boxFor(RepoForObjectbox::class.java).query().equal(RepoForObjectbox_.rootId, result.id).build().find()
                        result.items = list
                        Single.just(result)
                    } else {
                        Single.just(SearchResultForObjectbox())
                    }
                }).subscribeOn(Schedulers.io())
    }

}
