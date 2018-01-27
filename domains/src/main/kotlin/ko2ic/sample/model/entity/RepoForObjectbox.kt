package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import ko2ic.sample.model.PermissionDtoForObjectbox

@Entity
class RepoForObjectbox(

        @Id(assignable = true)
        var id: Long = 0,

        val name: String,

        @SerializedName("full_name")
        val fullName: String,

        @SerializedName("stargazers_count")
        val stars: Int,

        @SerializedName("permissions")
        val permissions: ToOne<PermissionDtoForObjectbox>,

        val root: ToOne<SearchResultForObjectbox>


)
