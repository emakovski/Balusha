package com.egor.balusha.dbpets

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PetsInfo::class], version = 1, exportSchema = false)
abstract class DatabasePetsInfo : RoomDatabase() {
    abstract fun getPetsInfoDao(): PetsInfoDao

    companion object {
        private var INSTANCE: DatabasePetsInfo? = null
        fun getDataBase(context: Context): DatabasePetsInfo {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DatabasePetsInfo::class.java,
                    "database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as DatabasePetsInfo
        }
    }
}