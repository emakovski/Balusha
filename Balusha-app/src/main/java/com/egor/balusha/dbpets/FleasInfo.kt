package com.egor.balusha.dbpets

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "fleas_info")
class FleasInfo(@ColumnInfo var fleas_name: String, @ColumnInfo var fleas_date: String) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()) {
        id = parcel.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(fleas_name)
        p0.writeString(fleas_date)
        p0.writeLong(id)
    }

    companion object CREATOR : Parcelable.Creator<FleasInfo> {
        override fun createFromParcel(parcel: Parcel): FleasInfo {
            return FleasInfo(parcel)
        }

        override fun newArray(size: Int): Array<FleasInfo?> {
            return arrayOfNulls(size)
        }
    }
}