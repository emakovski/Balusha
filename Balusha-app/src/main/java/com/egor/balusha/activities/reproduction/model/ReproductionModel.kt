package com.egor.balusha.activities.reproduction.model

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import com.egor.balusha.lib.DateHumanizer

class ReproductionModel (
    var id: Int?,
    puppiesRepro: String,
    commentRepr: String,
    estrusDate: Long? = null,
    matingDate: Long? = null,
    birthDateRepro: Long? = null
) : Observable {

    constructor() : this(
    id = null,
    puppiesRepro = "",
    commentRepr = ""
    )
    var puppies = puppiesRepro
    @Bindable get
    set(value) {
        registry.notifyChange(this, BR.puppies)
        field = value
    }

    var commentRepro = commentRepr
    @Bindable get
    set(value) {
        registry.notifyChange(this, BR.commentRepro)
        field = value
    }

    var dateEstrus = estrusDate
    @Bindable get
    set(value) {
        field = value
        registry.notifyChange(this, BR.dateEstrus)
        registry.notifyChange(this, BR.setHumaDatEstrus)
    }

    val setHumaDatEstrus: String
    @Bindable
    get() = if (dateEstrus != null) DateHumanizer.humanize(
        dateEstrus,
        DateHumanizer.TYPE_DD_MMM_YYYY
    ) else ""

    var dateMating = matingDate
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.dateMating)
            registry.notifyChange(this, BR.setHumaDatMating)
        }

    val setHumaDatMating: String
        @Bindable
        get() = if (dateMating != null) DateHumanizer.humanize(
            dateMating,
            DateHumanizer.TYPE_DD_MMM_YYYY
        ) else ""

    var dateBirthRepro = birthDateRepro
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.dateBirthRepro)
            registry.notifyChange(this, BR.setHumaDatBirthRepro)
        }

    val setHumaDatBirthRepro: String
        @Bindable
        get() = if (dateBirthRepro != null) DateHumanizer.humanize(
            dateBirthRepro,
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