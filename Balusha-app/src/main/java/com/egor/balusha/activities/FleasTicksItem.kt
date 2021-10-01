package com.egor.balusha.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.databinding.FleasInfoBinding
import com.egor.balusha.dbpets.FleasInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private const val RESULT_CODE_BUTTON_BACK = 3

class FleasTicksItem : AppCompatActivity() {
    private lateinit var binding: FleasInfoBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentTreatInfo: FleasInfo
    private var treatId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val fleasInfo = intent.getParcelableExtra<FleasInfo>("fleasInfo")
        if (fleasInfo != null) {
            currentTreatInfo = fleasInfo
            treatId = fleasInfo.id
            binding.titleInFleasInfo.text = fleasInfo.fleas_name
            binding.dateInFleasInfo.text = fleasInfo.fleas_date
        }
    }

    private fun setListeners() {
        binding.fabFleasInfo.setOnClickListener {
            backToPreviousActivity()
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}