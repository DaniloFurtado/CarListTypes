package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class PickUpLocation(
    @SerializedName("@Name")
    var name: String = ""
)