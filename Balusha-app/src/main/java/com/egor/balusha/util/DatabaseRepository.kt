package com.egor.balusha.util

import android.content.Context
import androidx.lifecycle.LiveData
import com.egor.balusha.dbpets.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//made with coroutines

class DatabaseRepository(private val scope: CoroutineScope) {
    companion object {
        private lateinit var database: DatabasePetsInfo
        fun initDatabase(context: Context) {
            database = DatabasePetsInfo.getDataBase(context)
        }
    }

    suspend fun addVaccine(info: VaccineInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getVaccineInfoDao().add(info) }
    }

    suspend fun updateVaccineInfo(info: VaccineInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getVaccineInfoDao().update(info) }
    }

    suspend fun deleteVaccine(info: VaccineInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getVaccineInfoDao().delete(info) }
    }

    suspend fun getAllVaccineList() = withContext(scope.coroutineContext + Dispatchers.IO) { database.getVaccineInfoDao().getAll() }


    suspend fun addHelminths(info: HelminthsInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getHelminthsInfoDao().add(info) }
    }

    suspend fun updateHelminthsInfo(info: HelminthsInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getHelminthsInfoDao().update(info) }
    }

    suspend fun deleteHelminths(info: HelminthsInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getHelminthsInfoDao().delete(info) }
    }

    suspend fun getAllHelminthsList() = withContext(scope.coroutineContext + Dispatchers.IO) { database.getHelminthsInfoDao().getAll() }







    suspend fun addRepro(info: ReproInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getReproInfoDao().add(info) }
    }

    suspend fun updateReproInfo(info: ReproInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getReproInfoDao().update(info) }
    }

    suspend fun deleteRepro(info: ReproInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getReproInfoDao().delete(info) }
    }

    suspend fun getAllReproList() = withContext(scope.coroutineContext + Dispatchers.IO) { database.getReproInfoDao().getAll() }


}