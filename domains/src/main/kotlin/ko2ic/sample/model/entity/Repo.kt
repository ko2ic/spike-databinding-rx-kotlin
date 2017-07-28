package ko2ic.sample.model.entity

import com.google.gson.annotations.SerializedName

open class Repo() {

    var id: Int = 0

    var name: String = ""

    @SerializedName("full_name")
    var fullName: String = ""

    var description: String = ""

    @SerializedName("stargazers_count")
    var stars: Int = 0


}