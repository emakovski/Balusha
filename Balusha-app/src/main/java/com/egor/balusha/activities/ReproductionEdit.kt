package com.egor.balusha.activities

import android.app.Activity
import android.app.DatePickerDialog
import java.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.R
import com.egor.balusha.databinding.ReproEditBinding
import com.egor.balusha.dbpets.ReproInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3
private const val RESULT_CODE_BUTTON_REMOVE = 7

class ReproductionEdit : AppCompatActivity() {
    private lateinit var binding: ReproEditBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentReproInfo: ReproInfo
    private var reproId: Long = 0
    private val cal: Calendar = Calendar.getInstance()
    private var picker: DatePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val reproInfo = intent.getParcelableExtra<ReproInfo>("reproInfo")
        if (reproInfo != null) {
            currentReproInfo = reproInfo
            reproId = reproInfo.id
            binding.dateEstrusReprEdit.setText(reproInfo.repro_estrus_date)
            binding.dateMatingReprEdit.setText(reproInfo.repro_mating_date)
            binding.dateBirthReprEdit.setText(reproInfo.repro_birth_date)
            binding.numberOfPuppiesReprEdit.setText(reproInfo.repro_puppies)
            binding.commentsReprEdit.setText(reproInfo.repro_comments)
        }
    }

    private fun setListeners() {
        binding.backToMenuReprEdit.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabReprEdit.setOnClickListener {
            editReproInfoAndBackToPreviousActivity()
        }
        binding.buttonDeleteReprEdit.setOnClickListener {
            createDialog()
        }
        binding.dateEstrusReprEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateEstrusReprEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.dateMatingReprEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateMatingReprEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
        binding.dateBirthReprEdit.setOnClickListener{
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val yearr = cal.get(Calendar.YEAR)
            picker = DatePickerDialog(this,
                { view, year, monthOfYear, dayOfMonth -> binding.dateBirthReprEdit.setText(dayOfMonth.toString() + "." + (monthOfYear + 1) + "." + year) }, yearr, month, day)
            picker!!.show()
        }
    }

    private fun editReproInfoAndBackToPreviousActivity() {
        val dateEst = binding.dateEstrusReprEdit.text.toString()
        val dateMat = binding.dateMatingReprEdit.text.toString()
        val dateBir = binding.dateBirthReprEdit.text.toString()
        val puppies = binding.numberOfPuppiesReprEdit.text.toString()
        val comm = binding.commentsReprEdit.text.toString()
        if (dateEst.isNotEmpty() && dateMat.isNotEmpty() && dateBir.isNotEmpty() && puppies.isNotEmpty() && comm.isNotEmpty()) {
            val reproInfo = ReproInfo(dateEst, dateMat, dateBir, puppies, comm).also { it.id = reproId }
            //made with coroutines
            activityScope.launch {
                repository.updateReproInfo(reproInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.updateReproInfo(reproInfo).subscribe {
//            setResult(Activity.RESULT_OK)
//            finish()
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
                    repository.deleteRepro(currentReproInfo)
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }

                //made with rx
//                repository.deleteRepro(currentReproInfo).subscribe {
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