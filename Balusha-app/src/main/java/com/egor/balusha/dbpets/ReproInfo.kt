package com.egor.balusha.dbpets

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repro_info")
data class ReproInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") var id: Int?,
    @ColumnInfo (name = "puppies") var repro_puppies: String,
    @ColumnInfo (name = "commentRepro") var repro_comments: String,
    @ColumnInfo (name = "dateEstrus") var repro_estrus_date: Long? = null,
    @ColumnInfo (name = "dateMating") var repro_mating_date: Long? = null,
    @ColumnInfo (name = "dateBirthRepro") var repro_birth_date: Long? = null
    )