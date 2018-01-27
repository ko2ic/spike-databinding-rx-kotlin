package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
class SearchResult : RealmModel {

    @PrimaryKey
    var query = ""

    @SerializedName("total_count")
    var totalCount: Int = 0

    @SerializedName("incomplete_results")
    var isIncompleteResults: Boolean = false

    @SerializedName("items")
    var items: RealmList<Repo> = RealmList()
}