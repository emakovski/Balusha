package com.egor.balusha.activities.helminths.repository

import androidx.lifecycle.LiveData
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.dbpets.HelminthsInfo
import com.egor.balusha.dbpets.HelminthsInfoDao
import com.egor.balusha.dbpets.RoomDB

object HelminthsRepository {

    fun addHelm(info: HelminthsInfo): Long {
        return getHelmDao().add(info)
    }

    fun getHelmForId(helmId: Int): HelminthsInfo? {
        return getHelmDao().getHelmForId(helmId)
    }

    fun getHelmModelForIdLive(helmId: Int): LiveData<HelminthsModel> {
        return getHelmDao().getHelmModelForIdLive(helmId)
    }

    fun deleteHelm(helmId: Int) {
        getHelmDao().delete(helmId)
    }

    fun getAllHelmList(): LiveData<List<HelminthsModel>> {
        return getHelmDao().getAll()
    }

    private fun getHelmDao(): HelminthsInfoDao {
        return RoomDB.getDatabaseInstance().getHelminthsInfoDao()
    }

}