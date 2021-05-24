package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.VaccineInfoAdapter
import com.egor.balusha.databinding.VaccineInfoBinding
import com.egor.balusha.dbpets.DatabasePetsInfo
import com.egor.balusha.dbpets.VaccineInfo
import com.egor.balusha.dbpets.VaccineInfoDao

private const val RESULT_CODE_BUTTON_BACK = 3

class VaccinationItem : AppCompatActivity() {
    private lateinit var binding: VaccineInfoBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var currentVaccineInfo: VaccineInfo
    private var vaccineId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineInfoBinding.inflate(layoutInflater)
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
            binding.titleInVaccineInfo.text = vaccineInfo.vaccine_name
            binding.dateInVaccineInfo.text = vaccineInfo.vaccine_date
            binding.typeVaccineInfo.text = vaccineInfo.vaccine_type
            binding.batchVaccineInfo.text = vaccineInfo.vaccine_batch
            binding.clinicVaccineInfo.text = vaccineInfo.vaccine_clinic
        }
    }

    private fun setListeners() {
        binding.fabVaccineInfo.setOnClickListener {
            backToPreviousActivity()
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}