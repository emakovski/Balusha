package com.egor.balusha.activities.helminths.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.activities.helminths.repository.HelminthsRepository

class HelminthsListViewModel: ViewModel() {
    var helmData: LiveData<List<HelminthsModel>> = HelminthsRepository.getAllHelmList()
}