package com.egor.balusha.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.HelminthsInfoBinding
import com.egor.balusha.dbpets.HelminthsInfo

private const val RESULT_CODE_BUTTON_BACK = 3

class HelminthTreatItem : AppCompatActivity() {
    private lateinit var binding: HelminthsInfoBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var currentTreatInfo: HelminthsInfo
    private var treatId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val helminthsInfo = intent.getParcelableExtra<HelminthsInfo>("helminthsInfo")
        if (helminthsInfo != null) {
            currentTreatInfo = helminthsInfo
            treatId = helminthsInfo.id
            binding.titleInHelminthsInfo.text = helminthsInfo.helm_name
            binding.dateInHelminthsInfo.text = helminthsInfo.helm_date
            binding.doseHelminthsInfo.text = helminthsInfo.helm_dose
        }
    }

    private fun setListeners() {
        binding.fabHelminthsInfo.setOnClickListener {
            backToPreviousActivity()
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}