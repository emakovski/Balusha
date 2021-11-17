package com.egor.balusha.activities.reproduction.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.activities.reproduction.repository.ReproductionRepository

class ReproductionListViewModel: ViewModel() {
    var reproData: LiveData<List<ReproductionModel>> = ReproductionRepository.getAllReproList()
}