package com.egor.balusha.util

import android.content.Context
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



    suspend fun addFleas(info: FleasInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getFleasInfoDao().add(info) }
    }

    suspend fun updateFleasInfo(info: FleasInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getFleasInfoDao().update(info) }
    }

    suspend fun deleteFleas(info: FleasInfo) {
        withContext(scope.coroutineContext + Dispatchers.IO) { database.getFleasInfoDao().delete(info) }
    }

    suspend fun getAllFleasList() = withContext(scope.coroutineContext + Dispatchers.IO) { database.getFleasInfoDao().getAll() }


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

//made with rx

//class DatabaseRepository {
//    companion object {
//        private lateinit var database: DatabasePetsInfo
//        fun initDatabase(context: Context) {
//            database = DatabasePetsInfo.getDataBase(context)
//        }
//    }
//
//    fun addVaccine(info: VaccineInfo): Completable = Completable.create {
//        database.getVaccineInfoDao().add(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun updateVaccineInfo(info: VaccineInfo): Completable = Completable.create {
//        database.getVaccineInfoDao().update(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//
//    fun deleteVaccine(info: VaccineInfo): Completable = Completable.create {
//        database.getVaccineInfoDao().delete(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun getAllVaccineList(): Single<List<VaccineInfo>> = Single.create<List<VaccineInfo>> {
//        val list = database.getVaccineInfoDao().getAll()
//        it.onSuccess(list)
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//
//    fun addHelminths(info: HelminthsInfo): Completable = Completable.create {
//        database.getHelminthsInfoDao().add(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun updateHelminthsInfo(info: HelminthsInfo): Completable = Completable.create {
//        database.getHelminthsInfoDao().update(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//
//    fun deleteHelminths(info: HelminthsInfo): Completable = Completable.create {
//        database.getHelminthsInfoDao().delete(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun getAllHelminthsList(): Single<List<HelminthsInfo>> = Single.create<List<HelminthsInfo>> {
//        val list = database.getHelminthsInfoDao().getAll()
//        it.onSuccess(list)
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//
//    fun addFleas(info: FleasInfo): Completable = Completable.create {
//        database.getFleasInfoDao().add(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun updateFleasInfo(info: FleasInfo): Completable = Completable.create {
//        database.getFleasInfoDao().update(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//
//    fun deleteFleas(info: FleasInfo): Completable = Completable.create {
//        database.getFleasInfoDao().delete(info)
//        it.onComplete()
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//
//    fun getAllFleasList(): Single<List<FleasInfo>> = Single.create<List<FleasInfo>> {
//        val list = database.getFleasInfoDao().getAll()
//        it.onSuccess(list)
//    }.subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//}