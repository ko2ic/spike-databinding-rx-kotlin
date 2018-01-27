package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
class SearchResultForObjectbox {

    @Id
    var id: Long = 0

    // TODO 本当はStringをpkにしたいができない。https://github.com/objectbox/objectbox-java/issues/167
    // TODO ユニークでもいいがまだサポートされていない https://github.com/objectbox/objectbox-java/issues/20
    var query = ""

    @SerializedName("total_count")
    var totalCount: Int = 0

    @SerializedName("incomplete_results")
    var isIncompleteResults: Boolean = false

    @SerializedName("items")
    var items: List<RepoForObjectbox> = arrayListOf()

    // TODO ToManyのJsonAdapterはインスタンスで親のentityを要求するので作成できないためこうしている
    // https://github.com/objectbox/objectbox-java/issues/104
    @Backlink(to = "root")
    lateinit var many: ToMany<RepoForObjectbox>

}