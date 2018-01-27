package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import ko2ic.sample.model.PermissionDto

@RealmClass
class Repo(

        @PrimaryKey
        var id: Int = 0,

        var name: String = "",

        @SerializedName("full_name")
        var fullName: String = "",

        @SerializedName("stargazers_count")
        var stars: Int = 0,

        @SerializedName("permissions")
        var permissions: PermissionDto? = PermissionDto()
) : RealmModel
