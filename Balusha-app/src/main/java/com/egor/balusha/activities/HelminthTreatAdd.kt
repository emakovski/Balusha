package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.HelminthsAddBinding
import com.egor.balusha.dbpets.HelminthsInfo

private const val RESULT_CODE_BUTTON_BACK = 3

class HelminthTreatAdd : AppCompatActivity() {
    private lateinit var binding: HelminthsAddBinding
    private lateinit var repository: DatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuHelminthsAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabHelminthsAdd.setOnClickListener {
            addHelminthsInfoAndBackToPreviousActivity()
        }
    }

    private fun addHelminthsInfoAndBackToPreviousActivity() {
        val name = binding.nameHelminthsAdd.text.toString()
        val date = binding.dateInHelminthsAdd.text.toString()
        val dose = binding.doseHelminthsAdd.text.toString()
        if (name.isNotEmpty() && date.isNotEmpty() && dose.isNotEmpty()) {
            val helminthsInfo = HelminthsInfo(name, date, dose)
            repository.addHelminths(helminthsInfo).subscribe {
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

}