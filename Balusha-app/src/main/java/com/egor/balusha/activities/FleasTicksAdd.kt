package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.FleasAddBinding
import com.egor.balusha.databinding.FleasAndTicksBinding
import com.egor.balusha.databinding.HelminthsAddBinding
import com.egor.balusha.dbpets.FleasInfo
import com.egor.balusha.dbpets.HelminthsInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3

class FleasTicksAdd : AppCompatActivity() {
    private lateinit var binding: FleasAddBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope

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