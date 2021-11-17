package com.egor.balusha.activities.reproduction.repository

import androidx.lifecycle.LiveData
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.dbpets.ReproInfo
import com.egor.balusha.dbpets.ReproInfoDao
import com.egor.balusha.dbpets.RoomDB

object ReproductionRepository {

    fun addRepro(info: ReproInfo): Long {
        return getReproDao().add(info)
    }

    fun getReproForId(reproId: Int): ReproInfo? {
        return getReproDao().getReproForId(reproId)
    }

    fun getReproModelForIdLive(reproId: Int): LiveData<ReproductionModel> {
        return getReproDao().getReproModelForIdLive(reproId)
    }

    fun deleteRepro(reproId: Int) {
        getReproDao().delete(reproId)
    }

    fun getAllReproList(): LiveData<List<ReproductionModel>> {
        return getReproDao().getAll()
    }

    private fun getReproDao(): ReproInfoDao {
        return RoomDB.getDatabaseInstance().getReproInfoDao()
    }
}