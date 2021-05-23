package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.databinding.VaccineAddBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.VaccineInfo
import com.egor.balusha.dbpets.VaccineInfoDao


private const val RESULT_CODE_BUTTON_BACK = 3

class VaccinationAdd : AppCompatActivity() {
    private lateinit var binding: VaccineAddBinding
    private lateinit var dataBase: DatabasePetsInfo
    private lateinit var vaccineInfoDAO: VaccineInfoDao
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBase = DatabasePetsInfo.getDataBase(applicationContext)
        vaccineInfoDAO = dataBase.getVaccineInfoDao()
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuVaccineAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabVaccineAdd.setOnClickListener {
            addVaccineInfoAndBackToPreviousActivity()
        }
        binding.radiogroupVaccineAdd.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply {
                type=text.toString()
        }}
    }

    private fun addVaccineInfoAndBackToPreviousActivity() {
        val name = binding.nameVaccineAdd.text.toString()
        val date = binding.dateInVaccineAdd.text.toString()
//        val type = if (binding.radioRabiesAdd.isChecked){
//            binding.radioRabiesAdd.text.toString()
//        } else {
//            binding.radioDhppiAdd.text.toString()
//        }
        val batch = binding.batchVaccineAdd.text.toString()
        val clinic = binding.clinicVaccineAdd.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && type.isNotEmpty() && batch.isNotEmpty() && clinic.isNotEmpty()) {
            val vaccineInfo = VaccineInfo(name, date, type, batch, clinic)
            vaccineInfoDAO.add(vaccineInfo)
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

}