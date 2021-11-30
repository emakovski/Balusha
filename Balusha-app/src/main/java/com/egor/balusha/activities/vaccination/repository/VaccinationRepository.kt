package com.egor.balusha.activities.vaccination.repository

import androidx.lifecycle.LiveData
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.dbpets.RoomDB
import com.egor.balusha.dbpets.VaccineInfo
import com.egor.balusha.dbpets.VaccineInfoDao

object VaccinationRepository {
    fun addVac(info: VaccineInfo): Long {
        return getVacDao().add(info)
    }

    fun getVacForId(vacId: Int): VaccineInfo? {
        return getVacDao().getVacForId(vacId)
    }

    fun getVacModelForIdLive(vacId: Int): LiveData<VaccinationModel> {
        return getVacDao().getVacModelForIdLive(vacId)
    }

    fun deleteVac(vacId: Int) {
        getVacDao().delete(vacId)
    }

    fun getAllVacList(): LiveData<List<VaccinationModel>> {
        return getVacDao().getAll()
    }

    private fun getVacDao(): VaccineInfoDao {
        return RoomDB.getDatabaseInstance().getVaccineInfoDao()
    }
}