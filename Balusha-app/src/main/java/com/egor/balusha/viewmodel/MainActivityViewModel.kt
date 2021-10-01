package com.egor.balusha.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egor.balusha.util.NotifRepository

class MainActivityViewModel : ViewModel() {
    var mNotif: LiveData<List<NotifModel>> = NotifRepository.getAllNotifLive()
}