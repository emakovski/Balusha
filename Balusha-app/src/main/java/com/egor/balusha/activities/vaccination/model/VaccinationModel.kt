package com.egor.balusha.activities.vaccination.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import com.egor.balusha.lib.DateHumanizer

class VaccinationModel (
    var id: Int?,
    vacName: String,
    vacBatch: String,
    vacClinic: String,
    vacType: String,
    vacDate: Long? = null
) : Observable {
    constructor() : this(
        id = null,
        vacName = "",
        vacBatch = "",
        vacClinic = "",
        vacType = ""
    )
    var nameVac = vacName
    @Bindable get
    set(value) {
        registry.notifyChange(this, BR.nameVac)
        field = value
    }

    var typeVac = vacType
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.typeVac)
            field = value
        }

    var batchVac = vacBatch
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.batchVac)
            field = value
        }

    var clinicVac = vacClinic
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.clinicVac)
            field = value
        }

    var dateVac = vacDate
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.dateVac)
            registry.notifyChange(this, BR.setHumaDatVac)
        }

    val setHumaDatVac: String
        @Bindable
        get() = if (dateVac != null) DateHumanizer.humanize(
            dateVac,
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