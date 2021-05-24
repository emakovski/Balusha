package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface HelminthsInfoDao {
    @Query("SELECT * FROM helminths_info")
    fun getAll(): List<HelminthsInfo>

    @Delete
    fun delete(helminthsInfo: HelminthsInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: HelminthsInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(helminthsInfo: HelminthsInfo)
}