package com.org.wfnr_2024.Model.SessionModel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HydraMember(
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    val accessRestricted: Boolean,
    val begin: String,
    val chargeable: Boolean,
    val date: String,
    val end: String,
    @SerializedName("id") val id1: String,
    val isHighlight: Boolean,
    val languages: List<String>,
    val listContentText: String?,
    val logo: String?,
    val maximumParticipants: Int?,
    val programAreas: List<String>,
    val redirectUrl: String?,
    val resourceType: String,
    val room: Room,
    val subtitle: String?,
    val timeslotTitle: String,
    val title: String,
    val topics: List<String>,
    @SerializedName("type") val type1: Type
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createStringArrayList()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readParcelable(Room::class.java.classLoader)!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readParcelable(Type::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeByte(if (accessRestricted) 1 else 0)
        parcel.writeString(begin)
        parcel.writeByte(if (chargeable) 1 else 0)
        parcel.writeString(date)
        parcel.writeString(end)
        parcel.writeString(id1)
        parcel.writeByte(if (isHighlight) 1 else 0)
        parcel.writeStringList(languages)
        parcel.writeString(listContentText)
        parcel.writeString(logo)
        parcel.writeValue(maximumParticipants)
        parcel.writeStringList(programAreas)
        parcel.writeString(redirectUrl)
        parcel.writeString(resourceType)
        parcel.writeParcelable(room, flags)
        parcel.writeString(subtitle)
        parcel.writeString(timeslotTitle)
        parcel.writeString(title)
        parcel.writeStringList(topics)
        parcel.writeParcelable(type1, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HydraMember> {
        override fun createFromParcel(parcel: Parcel): HydraMember {
            return HydraMember(parcel)
        }

        override fun newArray(size: Int): Array<HydraMember?> {
            return arrayOfNulls(size)
        }
    }
}
