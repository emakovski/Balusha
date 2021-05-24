package com.egor.balusha.dbpets

import androidx.room.*


@Dao
interface VaccineInfoDao {
    @Query("SELECT * FROM vaccine_info")
    fun getAll(): List<VaccineInfo>

    @Delete
    fun delete(vaccineInfo: VaccineInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: VaccineInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(vaccineInfo: VaccineInfo)
}