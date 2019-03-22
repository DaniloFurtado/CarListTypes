package desenv.danilo.modelbinding

import android.os.Parcel
import android.os.Parcelable

class AvaibleCoreBind(
    var upDateTime: String,
    var returnDateTime: String,
    var upLocation: String,
    var returnLocation: String,
    var vehicles: List<VehicleBind>
) : Parcelable {

    fun quantityVehicles() = this.vehicles.size.toString()


    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(VehicleBind.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(upDateTime)
        writeString(returnDateTime)
        writeString(upLocation)
        writeString(returnLocation)
        writeTypedList(vehicles)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AvaibleCoreBind> = object : Parcelable.Creator<AvaibleCoreBind> {
            override fun createFromParcel(source: Parcel): AvaibleCoreBind = AvaibleCoreBind(source)
            override fun newArray(size: Int): Array<AvaibleCoreBind?> = arrayOfNulls(size)
        }
    }
}