package com.egor.balusha.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.databinding.library.baseAdapters.BR
import com.egor.balusha.R
import com.egor.balusha.lib.DateHumanizer
import com.egor.balusha.receiver.setTimeMillis
import com.egor.balusha.util.BalushaApplication
import com.egor.balusha.util.NotifColor
import java.util.*

class NotifModel(
    var Id: Int?,
    noteTitle: String,
    noteBody: String,
    noteColor: String,
    noteReminder: Long? = null,
    noteIsPinned: Boolean = false,
    notePinnedOn: Long? = null
) : Observable {

    constructor() : this(
        Id = null,
        noteTitle = "",
        noteBody = "",
        noteColor = BalushaApplication.getApplicationInstance().getString(
            R.string.note_color_green
        )
    )

    //<editor-fold desc="get set">
    var title = noteTitle
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.title)
            field = value
        }
    var body = noteBody
        @Bindable get
        set(value) {
            registry.notifyChange(this, BR.body)
            field = value
        }
    var color = noteColor
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.color)
            registry.notifyChange(this, BR.displayColor)
        }
    var reminder = noteReminder
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.reminder)
            registry.notifyChange(this, BR.displayReminder)
            registry.notifyChange(this, BR.reminderAdded)
            registry.notifyChange(this, BR.reminderDateValid)
        }
    var isPinned = noteIsPinned
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.pinned)
        }
    var pinnedOn = notePinnedOn
        @Bindable get
        set(value) {
            field = value
            registry.notifyChange(this, BR.pinnedOn)
        }
    //</editor-fold>

    //<editor-fold desc="Extras">
    val isReminderAdded
        @Bindable
        get() = !reminder?.toString().isNullOrEmpty()

    val displayReminder: String
        @Bindable
        get() = if (reminder != null) DateHumanizer.humanize(
            reminder,
            DateHumanizer.TYPE_DD_MMM_YYYY,
            DateHumanizer.TYPE_TIME_HH_MM_A
        ) else ""

    val displayColor
        @Bindable get() = NotifColor.getColorInt(color)

    val isReminderDateValid: Boolean
        @Bindable get() = Calendar.getInstance().before(
            Calendar.getInstance().setTimeMillis(reminder ?: Calendar.getInstance().timeInMillis)
        )
    //</editor-fold>

    //<editor-fold desc="Registry">
    @Transient
    private val registry: PropertyChangeRegistry = PropertyChangeRegistry()
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }
    //</editor-fold>
}