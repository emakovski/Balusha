package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
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
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateInFleasAdd.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun addFleasInfoAndBackToPreviousActivity() {
        val name = binding.nameFleasAdd.text.toString()
        val date = binding.dateInFleasAdd.text.toString()
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
}