package desenv.danilo.modelbinding

import android.os.Parcel
import android.os.Parcelable


class VehicleBind(
    var status: String,
    var arConditino: Boolean,
    var transmissionType: String,
    var fuelType: String,
    var passengerQuantity: String,
    var picturyUrl: String,
    var rateTotalAmount: String,
    var vendor: VendorBind,
    var makeModel: String
) : Parcelable, Comparable<Double> {
    override fun compareTo(other: Double): Int {
        return if (this.rateTotalAmount.toDouble() > other)
            1
        else
            0
    }

    constructor(source: Parcel) : this(
        source.readString(),
        1 == source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<VendorBind>(VendorBind::class.java.classLoader),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(status)
        writeInt((if (arConditino) 1 else 0))
        writeString(transmissionType)
        writeString(fuelType)
        writeString(passengerQuantity)
        writeString(picturyUrl)
        writeString(rateTotalAmount)
        writeParcelable(vendor, 0)
        writeString(makeModel)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VehicleBind> = object : Parcelable.Creator<VehicleBind> {
            override fun createFromParcel(source: Parcel): VehicleBind = VehicleBind(source)
            override fun newArray(size: Int): Array<VehicleBind?> = arrayOfNulls(size)
        }
    }
}