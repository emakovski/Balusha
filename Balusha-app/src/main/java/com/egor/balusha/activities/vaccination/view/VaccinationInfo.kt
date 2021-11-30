package com.egor.balusha.activities.vaccination.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.egor.balusha.R
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.activities.vaccination.repository.VaccinationRepository
import com.egor.balusha.activities.vaccination.viewmodel.VaccinationInfoViewModel
import com.egor.balusha.databinding.VaccineInfoBinding
import com.egor.balusha.dbpets.VaccineInfo
import com.egor.balusha.receiver.setFiled
import kotlinx.coroutines.*
import java.util.*

private const val RESULT_CODE_BUTTON_BACK = 3

class VaccinationInfo : AppCompatActivity() {
    private lateinit var binding: VaccineInfoBinding
    lateinit var vacModel: VaccinationInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }
    private fun getIntentData() {
        val vacId = intent.getIntExtra("vacId", 0)
        initViewModel(vacId)
    }

    private fun initViewModel(vacId: Int) {
        vacModel = ViewModelProvider(this).get(VaccinationInfoViewModel::class.java)
        vacModel.setVacId(vacId)
        vacModel.vacModel.observe(this,
            Observer { binding.vac = it ?: VaccinationModel() })
    }

    private fun initView() {
        binding.fabVaccineEdit.setOnClickListener {
            validateAndSaveVac()
        }
        binding.backToMenuVaccineInfo.setOnClickListener {
            backToPreviousActivity()
        }
        binding.radiogroupVaccineInfo.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                binding.vac?.typeVac=text.toString()
            }}
        setupDialogListeners()
    }


    private fun setupDialogListeners() {
        val cal = Calendar.getInstance()

        binding.dateInVaccineInfo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    binding.vac!!.dateVac = dateSelected(year, month, dayOfMonth)
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

    private fun validateAndSaveVac() {
        if (binding.vac!!.dateVac==null || binding.vac!!.nameVac.isBlank() || binding.vac!!.typeVac.isBlank()) {
            Toast.makeText(this, R.string.validation_new_fleas, Toast.LENGTH_SHORT).show()
        } else {

            CoroutineScope(Dispatchers.IO).launch {
                saveRepro()
            }
        }
    }

    private suspend fun saveRepro() {
        val vacModel = binding.vac
        val vac = VaccineInfo(vacModel!!.id, vacModel.nameVac, vacModel.batchVac, vacModel.clinicVac, vacModel.typeVac)
        vac.vaccine_date = vacModel.dateVac
        VaccinationRepository.addVac(info = vac)

        withContext(Dispatchers.Main) {
            Toast.makeText(this@VaccinationInfo, R.string.info_saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}