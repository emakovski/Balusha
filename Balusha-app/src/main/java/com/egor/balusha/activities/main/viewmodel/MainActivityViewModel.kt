package com.egor.balusha.activities.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.egor.balusha.activities.main.repository.NotifRepository
import com.egor.balusha.activities.main.model.NotifModel

class MainActivityViewModel : ViewModel() {
    var mNotif: LiveData<List<NotifModel>> = NotifRepository.getAllNotifLive()
}