package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class Vendor(
    @SerializedName("@Code")
    var code: String = "",
    @SerializedName("@Name")
    var name: String =""
)