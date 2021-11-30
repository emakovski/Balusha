package com.egor.balusha.activities.vaccination.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.activities.vaccination.repository.VaccinationRepository

class VaccinationListViewModel: ViewModel() {
    var vacData: LiveData<List<VaccinationModel>> = VaccinationRepository.getAllVacList()
}