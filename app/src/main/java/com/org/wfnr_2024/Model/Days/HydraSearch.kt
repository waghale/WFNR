package com.org.wfnr_2024.Model.Days

import android.os.Parcel
import android.os.Parcelable
import com.org.wfnr_2024.Model.SessionModel.HydraMapping
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

    companion object CREATOR : Parcelable.Creator<com.org.wfnr_2024.Model.Days.HydraSearch> {
        override fun createFromParcel(parcel: Parcel): com.org.wfnr_2024.Model.Days.HydraSearch? {
            return HydraSearch(parcel)
        }

        override fun newArray(size: Int): Array<com.org.wfnr_2024.Model.Days.HydraSearch?> {
            return arrayOfNulls(size)
        }
    }
}