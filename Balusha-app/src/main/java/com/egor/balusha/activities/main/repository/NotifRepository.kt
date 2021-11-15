package com.egor.balusha.activities.main.repository

import androidx.lifecycle.LiveData
import com.egor.balusha.dbpets.NotifDao
import com.egor.balusha.dbpets.NotifEntity
import com.egor.balusha.dbpets.RoomDB
import com.egor.balusha.activities.main.model.NotifModel

object NotifRepository {

    fun getNotifModelForId(notifId: Int): NotifModel? {
        return getNotifiDao().getNotifModelForId(notifId)
    }

    fun getNotifForId(notifId: Int): NotifEntity? {
        return getNotifiDao().getNotifForId(notifId)
    }

    fun getNotifModelForIdLive(notifId: Int): LiveData<NotifModel> {
        return getNotifiDao().getNotifModelForIdLive(notifId)
    }

    fun getAllNotifLive(): LiveData<List<NotifModel>> {
        return getNotifiDao().getAllNotifLive()
    }

    fun getAllNotif(): List<NotifModel> {
        return getNotifiDao().getAllNotif()
    }

    fun insertNotif(note: NotifEntity): Long {
        return getNotifiDao().insertNotif(note)
    }

    fun deleteNotif(notifId: Int) {
        getNotifiDao().deleteNotif(notifId)
    }

    private fun getNotifiDao(): NotifDao {
        return RoomDB.getDatabaseInstance().getNotifDao()
    }

}