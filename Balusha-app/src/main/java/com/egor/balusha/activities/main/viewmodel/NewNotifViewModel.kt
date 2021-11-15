package com.egor.balusha.activities.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.main.model.NotifModel
import com.egor.balusha.activities.main.repository.NotifRepository

class NewNotifViewModel : ViewModel() {
    private var mNotifId: MutableLiveData<Int> = MutableLiveData<Int>()
    var notifModel: LiveData<NotifModel> =
        Transformations.switchMap(mNotifId) { input -> NotifRepository.getNotifModelForIdLive(input) }

    fun setNotifId(notifId: Int) {
        mNotifId.value = notifId
    }
}