package com.egor.balusha.activities.helminths.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.egor.balusha.R
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.activities.helminths.repository.HelminthsRepository
import com.egor.balusha.activities.helminths.viewmodel.HelminthsInfoViewModel
import com.egor.balusha.databinding.HelminthsInfoBinding
import com.egor.balusha.dbpets.HelminthsInfo
import com.egor.balusha.receiver.setFiled
import kotlinx.coroutines.*
import java.util.*

private const val RESULT_CODE_BUTTON_BACK = 3

class HelminthTreatInfo : AppCompatActivity() {
    private lateinit var binding: HelminthsInfoBinding
    lateinit var helmModel: HelminthsInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }
    private fun getIntentData() {
        val helmId = intent.getIntExtra("helmId", 0)
        initViewModel(helmId)
    }

    private fun initViewModel(helmId: Int) {
        helmModel = ViewModelProvider(this).get(HelminthsInfoViewModel::class.java)
        helmModel.setHelmId(helmId)
        helmModel.helmModel.observe(this,
            Observer { binding.helm = it ?: HelminthsModel() })
    }

    private fun initView() {
        binding.fabHelminthsInfo.setOnClickListener {
            validateAndSaveHelm()
        }
        binding.backToMenuHelminthsInfo.setOnClickListener {
            backToPreviousActivity()
        }

        setupDialogListeners()
    }


    private fun setupDialogListeners() {
        val cal = Calendar.getInstance()

        binding.dateInHelminthsInfo.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this, { _, year, month, dayOfMonth ->
                    dateSelected(year, month, dayOfMonth)
                }
                , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }

    private fun dateSelected(year: Int, month: Int, dayOfMonth: Int) {
        val cal = Calendar.getInstance()
            .setFiled(Calendar.YEAR, year)
            .setFiled(Calendar.MONTH, month)
            .setFiled(Calendar.DAY_OF_MONTH, dayOfMonth)

        binding.helm!!.dateHelm = cal.timeInMillis

    }

    private fun validateAndSaveHelm() {
        if (binding.helm!!.nameHelm.isBlank()) {
            Toast.makeText(this, R.string.validation_new_fleas, Toast.LENGTH_SHORT)
                .show()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                saveHelm()
            }
        }
    }

    private suspend fun saveHelm() {
        val helmModel = binding.helm
        val helm = HelminthsInfo(helmModel!!.id, helmModel.nameHelm, helmModel.doseHelm)
        helm.helm_date = helmModel.dateHelm
        HelminthsRepository.addHelm(info = helm)

        withContext(Dispatchers.Main) {
            Toast.makeText(this@HelminthTreatInfo, R.string.info_saved, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}