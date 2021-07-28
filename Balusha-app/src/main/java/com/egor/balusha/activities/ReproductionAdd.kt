package com.egor.balusha.activities

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.databinding.ReproAddBinding
import com.egor.balusha.dbpets.ReproInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val RESULT_CODE_BUTTON_BACK = 3

class ReproductionAdd : AppCompatActivity() {
    private lateinit var binding: ReproAddBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
    }

    private fun setListeners() {
        binding.backToMenuReprAdd.setOnClickListener {
            backToPreviousActivity()
        }
        binding.fabReprAdd.setOnClickListener {
            addVaccineInfoAndBackToPreviousActivity()
        }
    }

    private fun addVaccineInfoAndBackToPreviousActivity() {
        val dateEst = binding.dateEstrusReprAdd.text.toString()
        val dateMat = binding.dateMatingReprAdd.text.toString()
        val dateBir = binding.dateBirthReprAdd.text.toString()
        val puppies = binding.numberOfPuppiesReprAdd.text.toString()
        val comm = binding.commentsReprAdd.text.toString()
        if (dateEst.isNotEmpty() && dateMat.isNotEmpty() && dateBir.isNotEmpty() && puppies.isNotEmpty() && comm.isNotEmpty()) {
            val reproInfo = ReproInfo(dateEst, dateMat, dateBir, puppies, comm)
            //made with coroutines
            activityScope.launch {
                repository.addRepro(reproInfo)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //made with rx
//            repository.addRepro(reproInfo).subscribe {
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