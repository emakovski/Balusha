package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.R
import com.egor.balusha.databinding.HelminthsEditBinding
import com.egor.balusha.dbpets.HelminthsInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3
private const val RESULT_CODE_BUTTON_REMOVE = 7

class HelminthTreatEdit : AppCompatActivity() {
    private lateinit var binding: HelminthsEditBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentHelminthsInfo: HelminthsInfo
    private var treatId: Long = 0
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val helminthsInfo = intent.getParcelableExtra<HelminthsInfo>("helminthsInfo")
        if (helminthsInfo != null) {
            currentHelminthsInfo = helminthsInfo
            treatId = helminthsInfo.id
            binding.nameHelminthsEdit.setText(helminthsInfo.helm_name)
            binding.dateInHelminthsEdit.setText(helminthsInfo.helm_date)
            binding.doseHelminthsEdit.setText(helminthsInfo.helm_dose)
        }
    }

    private fun setListeners() {
        binding.backToMenuHelminthsEdit.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabHelminthsEdit.setOnClickListener {
            editTreatInfoAndBackToPreviousActivity()
        }
        binding.buttonDeleteHelminthsEdit.setOnClickListener {
            createDialog()
        }
        binding.dateInHelminthsEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateInHelminthsEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun editTreatInfoAndBackToPreviousActivity() {
        val name = binding.nameHelminthsEdit.text.toString()
        val date = binding.dateInHelminthsEdit.text.toString()
        val dose = binding.doseHelminthsEdit.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && dose.isNotEmpty()) {
            val helminthsInfo = HelminthsInfo(name, date, dose).also { it.id = treatId }
            //made with coroutines
            activityScope.launch {
                repository.updateHelminthsInfo(helminthsInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            //made with rx
//            repository.updateHelminthsInfo(helminthsInfo).subscribe {
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
                //made with coroutines
                activityScope.launch {
                    repository.deleteHelminths(currentHelminthsInfo)
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }

                //made with rx
//                repository.deleteHelminths(currentHelminthsInfo).subscribe {
//                    setResult(RESULT_CODE_BUTTON_REMOVE)
//                    finish()
//                }
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }
}