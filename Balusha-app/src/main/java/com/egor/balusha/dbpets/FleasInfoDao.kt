package com.egor.balusha.dbpets

import androidx.lifecycle.LiveData
import androidx.room.*
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.main.model.NotifModel

@Dao
interface FleasInfoDao {
    @Query("SELECT * FROM fleas_info")
    fun getAll(): LiveData<List<FleasModel>>

    @Query("SELECT * FROM fleas_info WHERE Id=:fleasId")
    fun getFleasModelForIdLive(fleasId: Int): LiveData<FleasModel>

    @Query("SELECT * FROM fleas_info WHERE id=:fleasId")
    fun getFleasForId(fleasId: Int): FleasInfo?

    @Query("DELETE FROM fleas_info WHERE id=:fleasId")
    fun delete(fleasId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(fleas: FleasInfo): Long
}