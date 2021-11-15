package com.egor.balusha.dbpets

import androidx.lifecycle.LiveData
import androidx.room.*
import com.egor.balusha.activities.main.model.NotifModel

@Dao
interface NotifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotif(notif: NotifEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNotif(notif: List<NotifEntity>)

    @Query("SELECT * FROM NotifEntity WHERE Id=:notifId")
    fun getNotifModelForIdLive(notifId: Int): LiveData<NotifModel>

    @Query("SELECT * FROM NotifEntity WHERE Id=:notifId")
    fun getNotifModelForId(notifId: Int): NotifModel?

    @Query("SELECT * FROM NotifEntity WHERE Id=:notifId")
    fun getNotifForId(notifId: Int): NotifEntity?

    @Query(
        "SELECT * FROM NotifEntity " +
                "ORDER BY isPinned DESC, DATETIME(PinnedOn) DESC "
    )
    fun getAllNotifLive(): LiveData<List<NotifModel>>

    @Query("SELECT * FROM NotifEntity")
    fun getAllNotif(): List<NotifModel>

    @Query("DELETE FROM NotifEntity WHERE Id=:notifId")
    fun deleteNotif(notifId: Int)
}