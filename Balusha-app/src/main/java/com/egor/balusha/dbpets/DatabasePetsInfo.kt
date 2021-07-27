package com.egor.balusha.dbpets

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [VaccineInfo::class, HelminthsInfo::class, FleasInfo::class, ReproInfo::class], version = 4, exportSchema = false)
abstract class DatabasePetsInfo : RoomDatabase() {
    abstract fun getVaccineInfoDao(): VaccineInfoDao
    abstract fun getHelminthsInfoDao(): HelminthsInfoDao
    abstract fun getFleasInfoDao(): FleasInfoDao
    abstract fun getReproInfoDao(): ReproInfoDao

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