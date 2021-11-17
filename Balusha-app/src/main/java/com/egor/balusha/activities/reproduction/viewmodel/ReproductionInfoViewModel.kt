package com.egor.balusha.activities.reproduction.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.activities.reproduction.repository.ReproductionRepository

class ReproductionInfoViewModel: ViewModel() {
    private var reproId: MutableLiveData<Int> = MutableLiveData()

    var reproModel: LiveData<ReproductionModel> =
        Transformations.switchMap(reproId) { input -> ReproductionRepository.getReproModelForIdLive(input) }

    fun setHelmId(reproid: Int) {
        reproId.value = reproid
    }
}