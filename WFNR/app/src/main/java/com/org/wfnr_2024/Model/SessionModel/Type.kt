package com.org.wfnr_2024.Model.SessionModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Type(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val color: String?,
    val colorBackground: String?,
    val colorBorder: String?,
    val colorFont: String?,
    val colorHighlight: String?,
    val description: String?,
    val id_1: String?,
    val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
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

    companion object CREATOR : Parcelable.Creator<Type> {
        override fun createFromParcel(parcel: Parcel): Type {
            return Type(parcel)
        }

        override fun newArray(size: Int): Array<Type?> {
            return arrayOfNulls(size)
        }
    }
}