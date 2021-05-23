package com.egor.balusha.dbpets

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine_info")
class VaccineInfo(@ColumnInfo val vaccine_name: String,
                  @ColumnInfo var vaccine_date: String,
                  @ColumnInfo var vaccine_type: String,
                  @ColumnInfo var vaccine_batch: String,
                  @ColumnInfo var vaccine_clinic: String) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()) {
        id = parcel.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(vaccine_name)
        p0.writeString(vaccine_date)
        p0.writeString(vaccine_type)
        p0.writeString(vaccine_batch)
        p0.writeString(vaccine_clinic)
        p0.writeLong(id)
    }

    companion object CREATOR : Parcelable.Creator<VaccineInfo> {
        override fun createFromParcel(parcel: Parcel): VaccineInfo {
            return VaccineInfo(parcel)
        }

        override fun newArray(size: Int): Array<VaccineInfo?> {
            return arrayOfNulls(size)
        }
    }
}