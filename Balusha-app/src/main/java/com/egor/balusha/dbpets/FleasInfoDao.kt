package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface FleasInfoDao {
    @Query("SELECT * FROM fleas_info")
    fun getAll(): List<FleasInfo>

    @Query("SELECT fleas_name FROM fleas_info")
    fun getName(): String

    @Query("SELECT fleas_date FROM fleas_info")
    fun getDate(): String

    @Delete
    fun delete(fleasInfo: FleasInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: FleasInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(fleasInfo: FleasInfo)
}