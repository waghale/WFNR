package com.org.wfnr_2024.Model.Days

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HydraView(@SerializedName("@id") val id: String?,
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

    companion object CREATOR : Parcelable.Creator<com.org.wfnr_2024.Model.Days.HydraView> {
        override fun createFromParcel(parcel: Parcel): com.org.wfnr_2024.Model.Days.HydraView {
            return HydraView(parcel)
        }

        override fun newArray(size: Int): Array<com.org.wfnr_2024.Model.Days.HydraView?> {
            return arrayOfNulls(size)
        }
    }
}