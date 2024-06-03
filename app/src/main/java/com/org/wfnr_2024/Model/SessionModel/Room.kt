package com.org.wfnr_2024.Model.SessionModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("@id") val id: String?,
    @SerializedName("@type") val type: String?,
    val event: String?,
    val id_1: String?,
    val name: String?,
    val roomAddition: Any
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("roomAddition")
    ) {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}