package desenv.danilo.modelbinding

import android.os.Parcel
import android.os.Parcelable


class VendorBind(
    var nameVendor: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(nameVendor)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<VendorBind> = object : Parcelable.Creator<VendorBind> {
            override fun createFromParcel(source: Parcel): VendorBind = VendorBind(source)
            override fun newArray(size: Int): Array<VendorBind?> = arrayOfNulls(size)
        }
    }
}