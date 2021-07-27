package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.R
import com.egor.balusha.databinding.FleasEditBinding
import com.egor.balusha.databinding.HelminthsEditBinding
import com.egor.balusha.dbpets.FleasInfo
import com.egor.balusha.dbpets.HelminthsInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3
private const val RESULT_CODE_BUTTON_REMOVE = 7

class FleasTicksEdit : AppCompatActivity() {
    private lateinit var binding: FleasEditBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentFleasInfo: FleasInfo
    private var treatId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val fleasInfo = intent.getParcelableExtra<FleasInfo>("fleasInfo")
        if (fleasInfo != null) {
            currentFleasInfo = fleasInfo
            treatId = fleasInfo.id
            binding.nameFleasEdit.setText(fleasInfo.fleas_name)
            binding.dateInFleasEdit.setText(fleasInfo.fleas_date)
        }
    }

    private fun setListeners() {
        binding.backToMenuFleasEdit.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabFleasEdit.setOnClickListener {
            editTreatInfoAndBackToPreviousActivity()
        }
        binding.buttonDeleteFleasEdit.setOnClickListener {
            createDialog()
        }
    }

    private fun editTreatInfoAndBackToPreviousActivity() {
        val name = binding.nameFleasEdit.text.toString()
        val date = binding.dateInFleasEdit.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty()) {
            val fleasInfo = FleasInfo(name, date).also { it.id = treatId }
            //made with coroutines
            activityScope.launch {
                repository.updateFleasInfo(fleasInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.updateFleasInfo(fleasInfo).subscribe {
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
                    repository.deleteFleas(currentFleasInfo)
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }
                //made with rx
//                repository.deleteFleas(currentFleasInfo).subscribe {
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