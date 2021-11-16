package com.egor.balusha.activities.fleasticks.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import com.egor.balusha.lib.DateHumanizer

class FleasModel(
    var id: Int?,
    fleasName: String,
    fleasDate: Long? = null
) : Observable {

    constructor() : this(
        id = null,
        fleasName = ""
    )
    var nameFleas = fleasName
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.nameFleas)
            field = value
        }

    var dateFleas = fleasDate
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.dateFleas)
            registry.notifyChange(this, BR.setHumaDat)
        }

    val setHumaDat: String
        @Bindable
        get() = if (dateFleas != null) DateHumanizer.humanize(
            dateFleas,
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