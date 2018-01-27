package ko2ic.sample.model

import com.google.gson.annotations.SerializedName
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class PermissionDtoForObjectbox {
    @Id
    var id: Long = 0

    @SerializedName("admin")
    var haveAdmin: Boolean = false

    @SerializedName("push")
    var havePushAuthorizetion: Boolean = false

    @SerializedName("pull")
    var havePullAuthorizetion: Boolean = false

}