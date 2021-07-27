package com.egor.balusha.dbpets

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repro_info")
class ReproInfo(@ColumnInfo var repro_estrus_date: String,
                  @ColumnInfo var repro_mating_date: String,
                  @ColumnInfo var repro_birth_date: String,
                  @ColumnInfo var repro_puppies: String,
                  @ColumnInfo var repro_comments: String) : Parcelable {
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
        p0.writeString(repro_estrus_date)
        p0.writeString(repro_mating_date)
        p0.writeString(repro_birth_date)
        p0.writeString(repro_puppies)
        p0.writeString(repro_comments)
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