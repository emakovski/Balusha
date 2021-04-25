package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface PetsInfoDao {
    @Query("SELECT * FROM pets_info")
    fun getAll(): List<PetsInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: PetsInfo)

    @Update
    fun update(carInfo: PetsInfo)
}