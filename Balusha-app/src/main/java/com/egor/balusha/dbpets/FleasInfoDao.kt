package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface FleasInfoDao {
    @Query("SELECT * FROM fleas_info")
    fun getAll(): List<FleasInfo>

    @Delete
    fun delete(fleasInfo: FleasInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: FleasInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(fleasInfo: FleasInfo)
}