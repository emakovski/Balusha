package com.egor.balusha.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.databinding.VaccineInfoBinding
import com.egor.balusha.dbpets.VaccineInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private const val RESULT_CODE_BUTTON_BACK = 3

class VaccinationItem : AppCompatActivity() {
    private lateinit var binding: VaccineInfoBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentVaccineInfo: VaccineInfo
    private var vaccineId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
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