package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface HelminthsInfoDao {
    @Query("SELECT * FROM helminths_info")
    fun getAll(): List<HelminthsInfo>

    @Query("SELECT helm_name FROM helminths_info")
    fun getName(): String

    @Query("SELECT helm_date FROM helminths_info")
    fun getDate(): String

    @Query("SELECT helm_dose FROM helminths_info")
    fun getDose(): String

    @Delete
    fun delete(helminthsInfo: HelminthsInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: HelminthsInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(helminthsInfo: HelminthsInfo)
}