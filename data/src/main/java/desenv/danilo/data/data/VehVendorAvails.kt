package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class VehVendorAvails(
    @SerializedName("Vendor")
    var vendor: Vendor,
    @SerializedName("VehAvails")
    var vehAvails: List<VehAvails>
)
