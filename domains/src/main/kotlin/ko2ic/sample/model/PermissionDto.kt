package ko2ic.sample.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
class PermissionDto : RealmModel {
    @SerializedName("admin")
    var haveAdmin: Boolean = false

    @SerializedName("push")
    var havePushAuthorizetion: Boolean = false

    @SerializedName("pull")
    var havePullAuthorizetion: Boolean = false

}