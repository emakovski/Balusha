package com.egor.balusha.activities.vaccination.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.activities.vaccination.repository.VaccinationRepository

class VaccinationInfoViewModel: ViewModel() {
    private var vacId: MutableLiveData<Int> = MutableLiveData()

    var vacModel: LiveData<VaccinationModel> =
        Transformations.switchMap(vacId) { input -> VaccinationRepository.getVacModelForIdLive(input) }

    fun setVacId(vacid: Int) {
        vacId.value = vacid
    }
}