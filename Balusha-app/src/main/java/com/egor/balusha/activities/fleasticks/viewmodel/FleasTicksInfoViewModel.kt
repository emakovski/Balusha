package com.egor.balusha.activities.fleasticks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.fleasticks.repository.FleasRepository

class FleasTicksInfoViewModel: ViewModel() {
    private var fleasId: MutableLiveData<Int> = MutableLiveData()

    var fleasModel: LiveData<FleasModel> =
        Transformations.switchMap(fleasId) { input -> FleasRepository.getFleasModelForIdLive(input) }

    fun setFleasId(fleasid: Int) {
        fleasId.value = fleasid
    }
}