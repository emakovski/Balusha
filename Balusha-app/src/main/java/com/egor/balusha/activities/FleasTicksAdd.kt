package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.FleasAddBinding
import com.egor.balusha.dbpets.FleasInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


private const val RESULT_CODE_BUTTON_BACK = 3

class FleasTicksAdd : AppCompatActivity() {
    private lateinit var binding: FleasAddBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null
    private lateinit var date: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuFleasAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabFleasAdd.setOnClickListener {
            addFleasInfoAndBackToPreviousActivity()
        }
        binding.dateInFleasAdd.setOnClickListener{
//            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
//                cal.set(Calendar.YEAR, year)
//                cal.set(Calendar.MONTH, monthOfYear)
//                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//                val myFormat = "dd.mm.yyyy"
//                val sdf = SimpleDateFormat(myFormat, Locale.US)
//                binding.dateInFleasAdd.setText(sdf.format(cal.time))}
//
//            DatePickerDialog(this, dateSetListener,
//                cal.get(Calendar.YEAR),
//                cal.get(Calendar.MONTH),
//                cal.get(Calendar.DAY_OF_MONTH)).show()


            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateInFleasAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)}, yearr, month, day)
            picker!!.show()
            date=binding.dateInFleasAdd.text.toString()
        }
    }

    private fun addFleasInfoAndBackToPreviousActivity() {
        val name = binding.nameFleasAdd.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty()) {
            val fleasInfo = FleasInfo(name, date)
            //made with coroutines
            activityScope.launch {
                repository.addFleas(fleasInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.addFleas(fleasInfo).subscribe {
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
    private fun updateLabel() {
        val myFormat = "dd.mm.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.dateInFleasAdd.setText(sdf.format(cal.time))
        //(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year)
    }

}