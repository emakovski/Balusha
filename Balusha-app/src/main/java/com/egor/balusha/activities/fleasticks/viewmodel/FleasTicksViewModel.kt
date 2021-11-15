package com.egor.balusha.activities.fleasticks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.fleasticks.repository.FleasRepository
import com.egor.balusha.activities.main.model.NotifModel
import com.egor.balusha.activities.main.repository.NotifRepository
import com.egor.balusha.dbpets.FleasInfo
import com.egor.balusha.util.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FleasTicksViewModel: ViewModel() {
    var fleasData: LiveData<List<FleasModel>> = FleasRepository.getAllFleasList()

    private var fleasId: MutableLiveData<Int> = MutableLiveData()

    var fleasModel: LiveData<FleasModel> =
        Transformations.switchMap(fleasId) { input -> FleasRepository.getFleasModelForIdLive(input) }

    fun setFleasId(fleasid: Int) {
        fleasId.value = fleasid
    }
}