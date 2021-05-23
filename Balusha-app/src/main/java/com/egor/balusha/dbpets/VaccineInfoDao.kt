package com.egor.balusha.dbpets

import androidx.room.*


@Dao
interface VaccineInfoDao {
    @Query("SELECT * FROM vaccine_info")
    fun getAll(): List<VaccineInfo>

    @Query("SELECT vaccine_name FROM vaccine_info")
    fun getName(): String

    @Query("SELECT vaccine_date FROM vaccine_info")
    fun getDate(): String

    @Query("SELECT vaccine_type FROM vaccine_info")
    fun getType(): String

    @Query("SELECT vaccine_batch FROM vaccine_info")
    fun getBatch(): String

    @Query("SELECT vaccine_clinic FROM vaccine_info")
    fun getClinic(): String

    @Delete
    fun delete(vaccineInfo: VaccineInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: VaccineInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(vaccineInfo: VaccineInfo)
}