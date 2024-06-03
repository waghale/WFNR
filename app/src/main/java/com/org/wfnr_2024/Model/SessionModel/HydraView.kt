package com.org.wfnr_2024.Model.SessionModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HydraView(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<HydraView> {
        override fun createFromParcel(parcel: Parcel): HydraView {
            return HydraView(parcel)
        }

        override fun newArray(size: Int): Array<HydraView?> {
            return arrayOfNulls(size)
        }
    }
}