package com.egor.balusha.dbpets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "helminths_info")
data class HelminthsInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int?,
    @ColumnInfo (name = "nameHelm") var helm_name: String,
    @ColumnInfo (name = "doseHelm") var helm_dose: String,
    @ColumnInfo (name = "dateHelm") var helm_date: Long? = null
    )