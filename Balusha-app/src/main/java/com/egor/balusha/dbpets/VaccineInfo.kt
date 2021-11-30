package com.egor.balusha.dbpets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vaccine_info")
data class VaccineInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int?,
    @ColumnInfo (name = "nameVac") var vaccine_name: String,
    @ColumnInfo (name = "batchVac") var vaccine_batch: String,
    @ColumnInfo (name = "clinicVac") var vaccine_clinic: String,
    @ColumnInfo (name = "typeVac") var vaccine_type: String,
    @ColumnInfo (name = "dateVac") var vaccine_date: Long? = null
    )