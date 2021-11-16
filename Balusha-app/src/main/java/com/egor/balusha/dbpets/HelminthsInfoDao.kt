package com.egor.balusha.dbpets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egor.balusha.activities.helminths.model.HelminthsModel

@Dao
interface HelminthsInfoDao {
    @Query("SELECT * FROM helminths_info")
    fun getAll(): LiveData<List<HelminthsModel>>

    @Query("SELECT * FROM helminths_info WHERE Id=:helmId")
    fun getHelmModelForIdLive(helmId: Int): LiveData<HelminthsModel>

    @Query("SELECT * FROM helminths_info WHERE id=:helmId")
    fun getHelmForId(helmId: Int): HelminthsInfo?

    @Query("DELETE FROM helminths_info WHERE id=:helmId")
    fun delete(helmId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(helm: HelminthsInfo): Long
}