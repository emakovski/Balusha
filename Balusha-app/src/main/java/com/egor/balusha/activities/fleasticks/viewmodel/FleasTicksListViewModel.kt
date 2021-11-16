package com.egor.balusha.activities.fleasticks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.fleasticks.repository.FleasRepository

class FleasTicksListViewModel: ViewModel() {
    var fleasData: LiveData<List<FleasModel>> = FleasRepository.getAllFleasList()
}