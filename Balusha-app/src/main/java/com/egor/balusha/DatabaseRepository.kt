package com.egor.balusha

import android.content.Context
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.FleasInfo
import com.egor.balusha.dbpets.HelminthsInfo
import com.egor.balusha.dbpets.VaccineInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class DatabaseRepository {
    companion object {
        private lateinit var database: DatabasePetsInfo
        fun initDatabase(context: Context) {
            database = DatabasePetsInfo.getDataBase(context)
        }
    }

    fun addVaccine(info: VaccineInfo): Completable = Completable.create {
        database.getVaccineInfoDao().add(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun updateVaccineInfo(info: VaccineInfo): Completable = Completable.create {
        database.getVaccineInfoDao().update(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteVaccine(info: VaccineInfo): Completable = Completable.create {
        database.getVaccineInfoDao().delete(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getAllVaccineList(): Single<List<VaccineInfo>> = Single.create<List<VaccineInfo>> {
        val list = database.getVaccineInfoDao().getAll()
        it.onSuccess(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun addHelminths(info: HelminthsInfo): Completable = Completable.create {
        database.getHelminthsInfoDao().add(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun updateHelminthsInfo(info: HelminthsInfo): Completable = Completable.create {
        database.getHelminthsInfoDao().update(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteHelminths(info: HelminthsInfo): Completable = Completable.create {
        database.getHelminthsInfoDao().delete(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getAllHelminthsList(): Single<List<HelminthsInfo>> = Single.create<List<HelminthsInfo>> {
        val list = database.getHelminthsInfoDao().getAll()
        it.onSuccess(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun addFleas(info: FleasInfo): Completable = Completable.create {
        database.getFleasInfoDao().add(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun updateFleasInfo(info: FleasInfo): Completable = Completable.create {
        database.getFleasInfoDao().update(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


    fun deleteFleas(info: FleasInfo): Completable = Completable.create {
        database.getFleasInfoDao().delete(info)
        it.onComplete()
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getAllFleasList(): Single<List<FleasInfo>> = Single.create<List<FleasInfo>> {
        val list = database.getFleasInfoDao().getAll()
        it.onSuccess(list)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}