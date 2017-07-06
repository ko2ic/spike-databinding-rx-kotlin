package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SearchResult : RealmObject() {

    @PrimaryKey
    var query = ""

    @SerializedName("total_count")
    var totalCount: Int = 0

    @SerializedName("incomplete_results")
    var isIncompleteResults: Boolean = false

    var items: RealmList<Repo> = RealmList()
}