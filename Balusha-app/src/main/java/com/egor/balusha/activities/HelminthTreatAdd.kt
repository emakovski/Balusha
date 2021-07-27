package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
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