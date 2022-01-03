package com.egor.balusha.activities.fleasticks.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.egor.balusha.R
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.fleasticks.repository.FleasRepository
import com.egor.balusha.activities.fleasticks.viewmodel.FleasTicksInfoViewModel
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.databinding.FleasInfoBinding
import com.egor.balusha.dbpets.FleasInfo
import com.egor.balusha.receiver.setFiled
import kotlinx.android.synthetic.main.notification_add.*
import kotlinx.coroutines.*
import java.util.*


class FleasTicksInfo : AppCompatActivity() {
    private lateinit var binding: FleasInfoBinding
    lateinit var fleasModel: FleasTicksInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }

    private fun getIntentData() {
        val fleasId = intent.getIntExtra("fleasId", 0)
        initViewModel(fleasId)
    }

    private fun initViewModel(fleasId: Int) {
        fleasModel = ViewModelProvider(this).get(FleasTicksInfoViewModel::class.java)
        fleasModel.setFleasId(fleasId)
        fleasModel.fleasModel.observe(this,
            Observer { binding.fleas = it ?: FleasModel() })
    }

    private fun initView() {
        binding.fabFleasInfo.setOnClickListener {
            validateAndSaveFleas()
        }
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, FleasTicksList::class.java))
        }

        setupDialogListeners()
    }


    private fun setupDialogListeners() {
        val cal = Calendar.getInstance()

        binding.dateInFleasInfo.setOnClickListener {
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

        binding.fleas!!.dateFleas = cal.timeInMillis

    }

    private fun validateAndSaveFleas() {
        if (binding.fleas!!.nameFleas.isBlank()) {
            Toast.makeText(this, R.string.validation_new_fleas, Toast.LENGTH_SHORT)
                .show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                saveFleas()
            }
        }
    }

    private suspend fun saveFleas() {
        val fleasModel = binding.fleas
        val fleas = FleasInfo(fleasModel!!.id, fleasModel.nameFleas)
        fleas.fleas_date = fleasModel.dateFleas
        FleasRepository.addFleas(info = fleas)

        withContext(Dispatchers.Main) {
            Toast.makeText(this@FleasTicksInfo, R.string.info_saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}