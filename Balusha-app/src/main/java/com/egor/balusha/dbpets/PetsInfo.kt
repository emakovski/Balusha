package com.egor.balusha.dbpets

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets_info")
class PetsInfo(@ColumnInfo val pathToPicture: String,
              @ColumnInfo var name: String,
              @ColumnInfo var date_of_birth: String,
              @ColumnInfo var sex: String,
               @ColumnInfo var breed: String,
               @ColumnInfo var color: String,
               @ColumnInfo var tag_number: String,
               @ColumnInfo var marks: String,
               @ColumnInfo var pedigree_number: String,
               @ColumnInfo var chip_number: String,
               @ColumnInfo var chip_date: String,
               @ColumnInfo var chip_location: String,
              @ColumnInfo var owners_comment: String) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
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
        p0.writeString(pathToPicture)
        p0.writeString(name)
        p0.writeString(date_of_birth)
        p0.writeString(sex)
        p0.writeString(breed)
        p0.writeString(color)
        p0.writeString(tag_number)
        p0.writeString(marks)
        p0.writeString(pedigree_number)
        p0.writeString(chip_number)
        p0.writeString(chip_date)
        p0.writeString(chip_location)
        p0.writeString(owners_comment)
        p0.writeLong(id)
    }

    companion object CREATOR : Parcelable.Creator<PetsInfo> {
        override fun createFromParcel(parcel: Parcel): PetsInfo {
            return PetsInfo(parcel)
        }

        override fun newArray(size: Int): Array<PetsInfo?> {
            return arrayOfNulls(size)
        }
    }
}