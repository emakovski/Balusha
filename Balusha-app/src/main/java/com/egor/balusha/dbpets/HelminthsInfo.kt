package com.egor.balusha.dbpets

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "helminths_info")
class HelminthsInfo(@ColumnInfo val helm_name: String,
                  @ColumnInfo var helm_date: String,
                  @ColumnInfo var helm_dose: String) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()) {
        id = parcel.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(helm_name)
        p0.writeString(helm_date)
        p0.writeString(helm_dose)
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