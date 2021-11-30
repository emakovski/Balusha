package com.egor.balusha.dbpets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.egor.balusha.activities.vaccination.model.VaccinationModel


@Dao
interface VaccineInfoDao {
    @Query("SELECT * FROM vaccine_info")
    fun getAll(): LiveData<List<VaccinationModel>>

    @Query("SELECT * FROM vaccine_info WHERE Id=:vacId")
    fun getVacModelForIdLive(vacId: Int): LiveData<VaccinationModel>

    @Query("SELECT * FROM vaccine_info WHERE id=:vacId")
    fun getVacForId(vacId: Int): VaccineInfo?

    @Query("DELETE FROM vaccine_info WHERE id=:vacId")
    fun delete(vacId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vac: VaccineInfo): Long
}