package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.R
import com.egor.balusha.databinding.VaccineEditBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.VaccineInfo
import com.egor.balusha.dbpets.VaccineInfoDao

private const val RESULT_CODE_BUTTON_BACK = 3
private const val RESULT_CODE_BUTTON_REMOVE = 7

class VaccinationEdit : AppCompatActivity() {
    private lateinit var binding: VaccineEditBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var currentVaccineInfo: VaccineInfo
    private var vaccineId: Long = 0
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val vaccineInfo = intent.getParcelableExtra<VaccineInfo>("vaccineInfo")
        if (vaccineInfo != null) {
            currentVaccineInfo = vaccineInfo
            vaccineId = vaccineInfo.id
            binding.nameVaccineEdit.setText(vaccineInfo.vaccine_name)
            binding.dateInVaccineEdit.setText(vaccineInfo.vaccine_date)
            binding.batchVaccineEdit.setText(vaccineInfo.vaccine_batch)
            binding.clinicVaccineEdit.setText(vaccineInfo.vaccine_clinic)
            if (binding.radioRabiesEdit.text.toString()==vaccineInfo.vaccine_type){
                binding.radioRabiesEdit.isChecked
            } else {
                binding.radioDhppiEdit.isChecked
            }
        }
    }

    private fun setListeners() {
        binding.backToMenuVaccineEdit.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabVaccineEdit.setOnClickListener {
            editVaccineInfoAndBackToPreviousActivity()
        }
        binding.radiogroupVaccineEdit.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                type=text.toString()
            }}
        binding.buttonDeleteVaccineEdit.setOnClickListener {
            createDialog()
        }
    }

    private fun editVaccineInfoAndBackToPreviousActivity() {
        val name = binding.nameVaccineEdit.text.toString()
        val date = binding.dateInVaccineEdit.text.toString()
//        val type = if (binding.radioRabiesEdit.isChecked){
//            binding.radioRabiesEdit.text.toString()
//        } else {
//            binding.radioDhppiEdit.text.toString()
//        }
        val batch = binding.batchVaccineEdit.text.toString()
        val clinic = binding.clinicVaccineEdit.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && type.isNotEmpty() && batch.isNotEmpty() && clinic.isNotEmpty()) {
            val vaccineInfo = VaccineInfo(name, date, type, batch, clinic).also { it.id = vaccineId }
            repository.updateVaccineInfo(vaccineInfo).subscribe {
            setResult(Activity.RESULT_OK)
            finish()
            }
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

    override fun onBackPressed() {
        setResult(RESULT_CODE_BUTTON_BACK)
        finish()
        super.onBackPressed()
    }
    private fun createDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.remove_item))
            .setMessage(getString(R.string.warning))
            .setPositiveButton("Apply"
            ) { dialogInterface, i ->
                repository.deleteVaccine(currentVaccineInfo).subscribe {
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }
}