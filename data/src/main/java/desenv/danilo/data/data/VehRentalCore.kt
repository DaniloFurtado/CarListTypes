package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class VehRentalCore(
    @SerializedName("@PickUpDateTime")
    var pickUpDateTime: String = "",
    @SerializedName("@ReturnDateTime")
    var returnDateTime: String = "",
    @SerializedName("PickUpLocation")
    var pickUpLocation: PickUpLocation,
    @SerializedName("ReturnLocation")
    var returnLocation: ReturnLocation
)
