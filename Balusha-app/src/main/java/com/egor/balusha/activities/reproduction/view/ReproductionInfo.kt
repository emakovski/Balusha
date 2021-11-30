package com.egor.balusha.activities.reproduction.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.egor.balusha.R
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.activities.reproduction.repository.ReproductionRepository
import com.egor.balusha.activities.reproduction.viewmodel.ReproductionInfoViewModel
import com.egor.balusha.databinding.ReproInfoBinding
import com.egor.balusha.dbpets.ReproInfo
import com.egor.balusha.receiver.setFiled
import kotlinx.coroutines.*
import java.util.*

private const val RESULT_CODE_BUTTON_BACK = 3

class ReproductionInfo : AppCompatActivity() {
    private lateinit var binding: ReproInfoBinding
    lateinit var reproModel: ReproductionInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }
    private fun getIntentData() {
        val reproId = intent.getIntExtra("reproId", 0)
        initViewModel(reproId)
    }

    private fun initViewModel(reproId: Int) {
        reproModel = ViewModelProvider(this).get(ReproductionInfoViewModel::class.java)
        reproModel.setReproId(reproId)
        reproModel.reproModel.observe(this,
            Observer { binding.repro = it ?: ReproductionModel() })
    }

    private fun initView() {
        binding.fabReprInfo.setOnClickListener {
            validateAndSaveRepro()
        }
        binding.backToMenuReprInfo.setOnClickListener {
            backToPreviousActivity()
        }

        setupDialogListeners()
    }


    private fun setupDialogListeners() {
        val cal = Calendar.getInstance()

        binding.dateEstrusReprInfo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.repro!!.dateEstrus = dateSelected(year, month, dayOfMonth)
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.dateMatingReprInfo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.repro!!.dateMating = dateSelected(year, month, dayOfMonth)
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
        binding.dateBirthReprInfo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.repro!!.dateBirthRepro = dateSelected(year, month, dayOfMonth)
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    private fun dateSelected(year: Int, month: Int, dayOfMonth: Int): Long {
        val cal = Calendar.getInstance()
            .setFiled(Calendar.YEAR, year)
            .setFiled(Calendar.MONTH, month)
            .setFiled(Calendar.DAY_OF_MONTH, dayOfMonth)

        return cal.timeInMillis

    }

    private fun validateAndSaveRepro() {
        if (binding.repro!!.dateBirthRepro==null || binding.repro!!.puppies.isBlank()) {
            Toast.makeText(this, R.string.validation_new_repro, Toast.LENGTH_SHORT)
                .show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                saveRepro()
            }
        }
    }

    private suspend fun saveRepro() {
        val reproModel = binding.repro
        val repro = ReproInfo(reproModel!!.id, reproModel.puppies, reproModel.commentRepro)
        repro.repro_estrus_date = reproModel.dateEstrus
        repro.repro_mating_date = reproModel.dateMating
        repro.repro_birth_date = reproModel.dateBirthRepro
        ReproductionRepository.addRepro(info = repro)

        withContext(Dispatchers.Main) {
            Toast.makeText(this@ReproductionInfo, R.string.info_saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}