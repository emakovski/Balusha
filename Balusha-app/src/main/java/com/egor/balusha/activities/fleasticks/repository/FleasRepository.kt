package com.egor.balusha.activities.fleasticks.repository

import androidx.lifecycle.LiveData
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.main.model.NotifModel
import com.egor.balusha.activities.main.repository.NotifRepository
import com.egor.balusha.dbpets.*

object FleasRepository {

    fun addFleas(info: FleasInfo): Long {
        return getFleasDao().add(info)
    }

    fun getFleasForId(fleasId: Int): FleasInfo? {
        return getFleasDao().getFleasForId(fleasId)
    }

    fun getFleasModelForIdLive(fleasId: Int): LiveData<FleasModel> {
        return getFleasDao().getFleasModelForIdLive(fleasId)
    }

    fun deleteFleas(fleasId: Int) {
        getFleasDao().delete(fleasId)
    }

    fun getAllFleasList(): LiveData<List<FleasModel>> {
        return getFleasDao().getAll()
    }

    private fun getFleasDao(): FleasInfoDao {
        return RoomDB.getDatabaseInstance().getFleasInfoDao()
    }
}