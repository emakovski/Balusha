package com.egor.balusha.activities.helminths.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import com.egor.balusha.lib.DateHumanizer

class HelminthsModel(
    var id: Int?,
    helmName: String,
    helmDose: String,
    helmDate: Long? = null
    ) : Observable {

        constructor() : this(
        id = null,
        helmName = "",
        helmDose = ""
        )
        var nameHelm = helmName
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.nameHelm)
            field = value
        }

        var doseHelm = helmDose
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.doseHelm)
            field = value
        }

        var dateHelm = helmDate
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.dateHelm)
            registry.notifyChange(this, BR.setHumaDatHelm)
        }

        val setHumaDatHelm: String
        @Bindable
        get() = if (dateHelm != null) DateHumanizer.humanize(
            dateHelm,
            DateHumanizer.TYPE_DD_MMM_YYYY
        ) else ""

        @Transient
        private val registry: PropertyChangeRegistry = PropertyChangeRegistry()
        override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
            registry.add(callback)
        }

        override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
            registry.remove(callback)
        }
}