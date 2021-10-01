package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.databinding.HelminthsAddBinding
import com.egor.balusha.dbpets.HelminthsInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3

class HelminthTreatAdd : AppCompatActivity() {
    private lateinit var binding: HelminthsAddBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuHelminthsAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabHelminthsAdd.setOnClickListener {
            addHelminthsInfoAndBackToPreviousActivity()
        }
        binding.dateInHelminthsAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateInHelminthsAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun addHelminthsInfoAndBackToPreviousActivity() {
        val name = binding.nameHelminthsAdd.text.toString()
        val date = binding.dateInHelminthsAdd.text.toString()
        val dose = binding.doseHelminthsAdd.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && dose.isNotEmpty()) {
            val helminthsInfo = HelminthsInfo(name, date, dose)
            //made with coroutines
            activityScope.launch {
                repository.addHelminths(helminthsInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

            //made with rx
//            repository.addHelminths(helminthsInfo).subscribe {
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