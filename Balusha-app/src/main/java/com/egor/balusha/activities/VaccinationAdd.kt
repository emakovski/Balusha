package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.VaccineAddBinding
import com.egor.balusha.dbpets.VaccineInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


private const val RESULT_CODE_BUTTON_BACK = 3

class VaccinationAdd : AppCompatActivity() {
    private lateinit var binding: VaccineAddBinding
    private lateinit var type: String
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccineAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
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
        binding.dateInVaccineAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateInVaccineAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun addVaccineInfoAndBackToPreviousActivity() {
        val name = binding.nameVaccineAdd.text.toString()
        val date = binding.dateInVaccineAdd.text.toString()
        val batch = binding.batchVaccineAdd.text.toString()
        val clinic = binding.clinicVaccineAdd.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && type.isNotEmpty() && batch.isNotEmpty() && clinic.isNotEmpty()) {
            val vaccineInfo = VaccineInfo(name, date, type, batch, clinic)
            //made with coroutines
            activityScope.launch {
                repository.addVaccine(vaccineInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.addVaccine(vaccineInfo).subscribe {
//                setResult(Activity.RESULT_OK)
//                finish()
//            }
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

}