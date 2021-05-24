package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.R
import com.egor.balusha.databinding.HelminthsEditBinding
import com.egor.balusha.dbpets.HelminthsInfo

private const val RESULT_CODE_BUTTON_BACK = 3
private const val RESULT_CODE_BUTTON_REMOVE = 7

class HelminthTreatEdit : AppCompatActivity() {
    private lateinit var binding: HelminthsEditBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var currentHelminthsInfo: HelminthsInfo
    private var treatId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
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
    }

    private fun editTreatInfoAndBackToPreviousActivity() {
        val name = binding.nameHelminthsEdit.text.toString()
        val date = binding.dateInHelminthsEdit.text.toString()
        val dose = binding.doseHelminthsEdit.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && dose.isNotEmpty()) {
            val helminthsInfo = HelminthsInfo(name, date, dose).also { it.id = treatId }
            repository.updateHelminthsInfo(helminthsInfo).subscribe {
                setResult(Activity.RESULT_OK)
                finish()
            }
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
                repository.deleteHelminths(currentHelminthsInfo).subscribe {
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }
}