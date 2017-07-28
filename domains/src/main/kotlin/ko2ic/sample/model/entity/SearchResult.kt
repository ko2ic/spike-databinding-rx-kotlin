package ko2ic.sample.model.entity

import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


open class SearchResult {

    @PrimaryKey
    var query = ""

    @SerializedName("total_count")
    var totalCount: Int = 0

    @SerializedName("incomplete_results")
    var isIncompleteResults: Boolean = false

    var items: List<Repo> = ArrayList()
}