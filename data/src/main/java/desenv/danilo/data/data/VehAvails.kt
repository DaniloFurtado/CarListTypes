package desenv.danilo.data.data

import com.google.gson.annotations.SerializedName

data class VehAvails(
    @SerializedName("@Status")
    var status: String = "",
    @SerializedName("Vehicle")
    var vehicle: Vehicle,
    @SerializedName("TotalCharge")
    var totalCharge: TotalCharge
)

data class TotalCharge(
    @SerializedName("@RateTotalAmount")
    var rateTotalAmount: Double,
    @SerializedName("@EstimatedTotalAmount")
    var estimatedTotalAmount: Double,
    @SerializedName("@CurrencyCode")
    var currencyCode: String

)

data class Vehicle(
    @SerializedName("@AirConditionInd")
    var airConditionInd: Boolean,
    @SerializedName("@TransmissionType")
    var transmissionType: String,
    @SerializedName("@FuelType")
    var fuelType: String,
    @SerializedName("@DriveType")
    var driveType: String,
    @SerializedName("@PassengerQuantity")
    var passengerQuantity: String,
    @SerializedName("@BaggageQuantity")
    var baggageQuantity: Int,
    @SerializedName("@Code")
    var code: String,
    @SerializedName("@CodeContext")
    var codeContext: String,
    @SerializedName("@DoorCount")
    var doorCount: Int,
    @SerializedName("VehMakeModel")
    var VehMakeModel: VehMakeModel,
    @SerializedName("PictureURL")
    var pictureURL: String
)


data class VehMakeModel(
    @SerializedName("@Name")
    var name: String
)
