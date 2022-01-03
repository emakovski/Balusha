package com.egor.balusha.activities.main.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.egor.balusha.R
import com.egor.balusha.activities.main.model.NotifModel
import com.egor.balusha.activities.main.repository.NotifRepository
import com.egor.balusha.activities.main.viewmodel.NewNotifViewModel
import com.egor.balusha.databinding.NotificationAddBinding
import com.egor.balusha.dbpets.NotifEntity
import com.egor.balusha.receiver.DateTimeUtil
import com.egor.balusha.receiver.setFiled
import kotlinx.android.synthetic.main.notification_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NewNotifActivity : AppCompatActivity() {

    lateinit var binding: NotificationAddBinding
    lateinit var mViewModel: NewNotifViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            enterTransition = Slide(Gravity.TOP)
            exitTransition = Slide(Gravity.BOTTOM)
        }

        binding = DataBindingUtil.setContentView(this, R.layout.notification_add)

        getIntentData()
        registerListeners()
    }

    private fun getIntentData() {
        val notifId = intent.getIntExtra("notifId", 0)
        registerViewModel(notifId)
    }

    private fun registerViewModel(notifId: Int) {
        mViewModel = ViewModelProvider(this).get(NewNotifViewModel::class.java)
        mViewModel.setNotifId(notifId)
        mViewModel.notifModel.observe(this,
            Observer { binding.note = it ?: NotifModel() })
    }

    private fun registerListeners() {
        fab_notif_add.setOnClickListener {
            validateAndSaveNotif()
        }
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        setupColorListeners()
        setupDialogListeners()
    }

    private fun setupColorListeners() {
        val onColorClickListener: View.OnClickListener = View.OnClickListener {
            binding.note!!.color = when (it) {
                btn_color_blue -> resources.getString(R.string.note_color_blue)
                btn_color_green -> resources.getString(R.string.note_color_green)
                btn_color_purple -> resources.getString(R.string.note_color_purple)
                btn_color_red -> resources.getString(R.string.note_color_red)
                btn_color_teal -> resources.getString(R.string.note_color_teal)
                btn_color_orange -> resources.getString(R.string.note_color_orange)
                else -> resources.getString(R.string.note_color_green)
            }
        }

        for (child in bottom_elements.children) {
            if (child is ImageView)
                child.setOnClickListener(onColorClickListener)
        }
    }

    private fun setupDialogListeners() {
        val cal = Calendar.getInstance()

        btn_reminder.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    dateSelected(year, month, dayOfMonth)
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }

    private fun dateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
            .setFiled(Calendar.YEAR, year)
            .setFiled(Calendar.MONTH, month)
            .setFiled(Calendar.DAY_OF_MONTH, dayOfMonth)

        binding.note!!.reminder = cal.timeInMillis

        val timePickerDialog = TimePickerDialog(
            this, { _, hourOfDay, minute ->
                timeSelected(hourOfDay, minute)
            }, 12, 0, Locale.getDefault() != Locale.US
        )

        timePickerDialog.show()
    }

    private fun timeSelected(hourOfDay: Int, minute: Int) {
        val cal = Calendar.getInstance()
        cal.timeInMillis = binding.note!!.reminder ?: Calendar.getInstance().timeInMillis

        cal.setFiled(Calendar.HOUR_OF_DAY, hourOfDay)
            .setFiled(Calendar.MINUTE, minute)

        binding.note!!.reminder = cal.timeInMillis
    }

    private fun validateAndSaveNotif() {
        if (binding.note!!.body.isBlank()) {
            Toast.makeText(this, R.string.validation_new_note, Toast.LENGTH_SHORT)
                .show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                saveNotif()
            }
        }
    }

    private suspend fun saveNotif() {
        val pair = getNotifFromNoteModel()
        val notifId = NotifRepository.insertNotif(note = pair.first)

        if (pair.first.Reminder != null && pair.second)
            DateTimeUtil.setAlarmForReminder(pair.first.Reminder!!, notifId = notifId.toInt())

        withContext(Dispatchers.Main) {
            Toast.makeText(this@NewNotifActivity, R.string.info_saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getNotifFromNoteModel(): Pair<NotifEntity, Boolean> {
        val notifModel = binding.note
        val note = NotifEntity(notifModel!!.Id, notifModel.title, notifModel.body, notifModel.color)
        note.Reminder = notifModel.reminder
        if (note.Title.isBlank()) {
            note.Title =
                note.Body.substring(0, if (note.Body.length < 24) note.Body.length else 24)
        }
        return Pair(note, notifModel.isReminderDateValid)
    }
}