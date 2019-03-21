package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class VehAvailRSCore(
    @SerializedName("VehRentalCore")
    var vehRentalCore: VehRentalCore,
    @SerializedName("VehVendorAvails")
    var vehVendorAvails: List<VehVendorAvails>
)
