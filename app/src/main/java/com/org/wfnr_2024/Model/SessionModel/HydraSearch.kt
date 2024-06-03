package com.org.wfnr_2024.Model.SessionModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HydraSearch(
    @SerializedName("@type") val type: String?,

    @SerializedName("hydra:mapping") val mapping: List<HydraMapping>,
    @SerializedName("hydra:template") val template: String?,
    @SerializedName("hydra:variableRepresentation") val variableRepresentation: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("mapping"),
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

    companion object CREATOR : Parcelable.Creator<HydraSearch> {
        override fun createFromParcel(parcel: Parcel): HydraSearch {
            return HydraSearch(parcel)
        }

        override fun newArray(size: Int): Array<HydraSearch?> {
            return arrayOfNulls(size)
        }
    }
}