package com.egor.balusha.dbpets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fleas_info")
data class FleasInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int?,
    @ColumnInfo (name = "nameFleas") var fleas_name: String,
    @ColumnInfo (name = "dateFleas") var fleas_date: Long? = null
)