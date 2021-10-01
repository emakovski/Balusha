package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.databinding.ReproAddBinding
import com.egor.balusha.dbpets.ReproInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3

class ReproductionAdd : AppCompatActivity() {
    private lateinit var binding: ReproAddBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuReprAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabReprAdd.setOnClickListener {
            addVaccineInfoAndBackToPreviousActivity()
        }
        binding.dateEstrusReprAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateEstrusReprAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.dateMatingReprAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateMatingReprAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.dateBirthReprAdd.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateBirthReprAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun addVaccineInfoAndBackToPreviousActivity() {
        val dateEst = binding.dateEstrusReprAdd.text.toString()
        val dateMat = binding.dateMatingReprAdd.text.toString()
        val dateBir = binding.dateBirthReprAdd.text.toString()
        val puppies = binding.numberOfPuppiesReprAdd.text.toString()
        val comm = binding.commentsReprAdd.text.toString()
        if (dateEst.isNotEmpty() && dateMat.isNotEmpty() && dateBir.isNotEmpty() && puppies.isNotEmpty() && comm.isNotEmpty()) {
            val reproInfo = ReproInfo(dateEst, dateMat, dateBir, puppies, comm)
            //made with coroutines
            activityScope.launch {
                repository.addRepro(reproInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.addRepro(reproInfo).subscribe {
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