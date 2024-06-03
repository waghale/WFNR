package org.impactindiafoundation.wfnr.Model

import android.os.Parcel
import android.os.Parcelable

data class Special_Interest_Group_Model(
    val title:String?=null,
    val description:String?=null,
    val email:ArrayList<Email>?=null,
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Email.CREATOR)
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeTypedList(email)
    }

    companion object CREATOR : Parcelable.Creator<Special_Interest_Group_Model> {
        override fun createFromParcel(parcel: Parcel): Special_Interest_Group_Model {
            return Special_Interest_Group_Model(parcel)
        }

        override fun newArray(size: Int): Array<Special_Interest_Group_Model?> {
            return arrayOfNulls(size)
        }
    }
}