package it.linksmt.teechecker.model

import android.os.Parcel
import android.os.Parcelable

class HardwareCheckItem( val descriptionResourceId : Int, val hintResourceId : Int, val status : Boolean ) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(descriptionResourceId)
        parcel.writeInt(hintResourceId)
        parcel.writeByte(if (status) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HardwareCheckItem> {
        override fun createFromParcel(parcel: Parcel): HardwareCheckItem {
            return HardwareCheckItem(parcel)
        }

        override fun newArray(size: Int): Array<HardwareCheckItem?> {
            return arrayOfNulls(size)
        }
    }
}