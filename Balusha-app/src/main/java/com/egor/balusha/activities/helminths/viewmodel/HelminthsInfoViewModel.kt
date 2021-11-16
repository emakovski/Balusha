package com.egor.balusha.activities.helminths.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.activities.helminths.repository.HelminthsRepository

class HelminthsInfoViewModel: ViewModel() {
    private var helmId: MutableLiveData<Int> = MutableLiveData()

    var helmModel: LiveData<HelminthsModel> =
        Transformations.switchMap(helmId) { input -> HelminthsRepository.getHelmModelForIdLive(input) }

    fun setHelmId(helmid: Int) {
        helmId.value = helmid
    }
}