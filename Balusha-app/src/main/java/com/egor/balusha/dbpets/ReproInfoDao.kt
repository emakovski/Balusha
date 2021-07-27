package com.egor.balusha.dbpets

import androidx.room.*

@Dao
interface ReproInfoDao {
    @Query("SELECT * FROM repro_info")
    fun getAll(): List<ReproInfo>

    @Delete
    fun delete(reproInfo: ReproInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(entity: ReproInfo)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(reproInfo: ReproInfo)
}