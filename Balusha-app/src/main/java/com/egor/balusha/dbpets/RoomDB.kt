package com.egor.balusha.dbpets

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.egor.balusha.R
import com.egor.balusha.lib.DateHumanizer
import com.egor.balusha.receiver.addField
import com.egor.balusha.util.BalushaApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

private const val DATABASE_NAME = "DatabasePetsInfo"

class RoomDB {
    private var appDatabase: DatabasePetsInfo? = null

    companion object {
        private var ourInstance: RoomDB? = null

        fun getDatabaseInstance(): DatabasePetsInfo {
            if (ourInstance == null)
                ourInstance = RoomDB()

            return ourInstance!!.appDatabase as DatabasePetsInfo
        }
    }

    init {
        val applicationInstant = BalushaApplication.getApplicationInstance()

        appDatabase =
            Room.databaseBuilder(applicationInstant, DatabasePetsInfo::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getDatabaseInstance().getNotifDao().insertAllNotif(populateNotes())
                        }
                    }
                })
                .build()
    }

    private fun populateNotes(): List<NotifEntity> {
        val contextResource = BalushaApplication.getApplicationInstance().resources
        val time = Calendar.getInstance().addField(Calendar.MINUTE, 5).timeInMillis

        return listOf(
            NotifEntity(
                Id = null,
                Title = contextResource.getString(R.string.note_one_title),
                Body = contextResource.getString(R.string.note_one_body),
                Color = contextResource.getString(R.string.note_color_green)
            ),
            NotifEntity(
                Id = null,
                Title = contextResource.getString(R.string.note_second_title),
                Body = contextResource.getString(R.string.note_second_body),
                Color = contextResource.getString(R.string.note_color_blue)
            ),
            NotifEntity(
                Id = null,
                Title = contextResource.getString(R.string.note_third_title),
                Body = contextResource.getString(
                    R.string.note_third_body,
                    DateHumanizer.humanize(
                        time,
                        DateHumanizer.TYPE_DD_MMM_YYYY,
                        DateHumanizer.TYPE_TIME_HH_MM_A
                    )
                ),
                Color = contextResource.getString(R.string.note_color_orange),
                Reminder = time
            )
        )
    }

}