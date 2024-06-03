package com.org.wfnr_2024.Model.SessionModel



import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SessionDataModel(
    @SerializedName("@context") val context: String,
    @SerializedName("@id") val id: String,
    @SerializedName("@type") val type: String,
    @SerializedName("hydra:member") val member: List<HydraMember>,
    @SerializedName("hydra:search") val search: HydraSearch,
    @SerializedName("hydra:totalItems") val totalItems: Int,
    @SerializedName("hydra:view") val view: HydraView
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        // Deserialize list of HydraMember objects
        parcel.createTypedArrayList(HydraMember.CREATOR)!!,
        // Deserialize HydraSearch object
        parcel.readParcelable(HydraSearch::class.java.classLoader)!!,
        parcel.readInt(),
        // Deserialize HydraView object
        parcel.readParcelable(HydraView::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(context)
        parcel.writeString(id)
        parcel.writeString(type)
        // Serialize list of HydraMember objects
        parcel.writeTypedList(member)
        // Serialize HydraSearch object
        parcel.writeParcelable(search, flags)
        parcel.writeInt(totalItems)
        // Serialize HydraView object
        parcel.writeParcelable(view, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SessionDataModel> {
        override fun createFromParcel(parcel: Parcel): SessionDataModel {
            return SessionDataModel(parcel)
        }

        override fun newArray(size: Int): Array<SessionDataModel?> {
            return arrayOfNulls(size)
        }
    }
}
