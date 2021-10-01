package com.egor.balusha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.egor.balusha.util.NotifRepository

class NewNotifViewModel : ViewModel() {
    private var mNotifId: MutableLiveData<Int> = MutableLiveData<Int>()
    var notifModel: LiveData<NotifModel> =
        Transformations.switchMap(mNotifId) { input -> NotifRepository.getNotifModelForIdLive(input) }

    fun setNotifId(notifId: Int) {
        mNotifId.value = notifId
    }
}