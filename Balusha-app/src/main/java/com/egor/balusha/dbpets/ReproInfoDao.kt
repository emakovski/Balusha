package com.egor.balusha.dbpets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egor.balusha.activities.reproduction.model.ReproductionModel

@Dao
interface ReproInfoDao {
    @Query("SELECT * FROM repro_info")
    fun getAll(): LiveData<List<ReproductionModel>>

    @Query("SELECT * FROM repro_info WHERE Id=:reproId")
    fun getReproModelForIdLive(reproId: Int): LiveData<ReproductionModel>

    @Query("SELECT * FROM repro_info WHERE id=:reproId")
    fun getReproForId(reproId: Int): ReproInfo?

    @Query("DELETE FROM repro_info WHERE id=:reproId")
    fun delete(reproId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(repro: ReproInfo): Long
}