package com.egor.balusha.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.databinding.ReproInfoBinding
import com.egor.balusha.dbpets.ReproInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

private const val RESULT_CODE_BUTTON_BACK = 3

class ReproductionItem : AppCompatActivity() {
    private lateinit var binding: ReproInfoBinding
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope
    private lateinit var currentReproInfo: ReproInfo
    private var reproId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproInfoBinding.inflate(layoutInflater)
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
            binding.dateEstrusReprInfo.text = reproInfo.repro_estrus_date
            binding.dateMatingReprInfo.text = reproInfo.repro_mating_date
            binding.dateBirthReprInfo.text = reproInfo.repro_birth_date
            binding.numberOfPuppiesReprInfo.text = reproInfo.repro_puppies
            binding.commentsReprInfo.text = reproInfo.repro_comments
        }
    }

    private fun setListeners() {
        binding.fabReprInfo.setOnClickListener {
            backToPreviousActivity()
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}