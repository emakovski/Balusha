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

private const val RESULT_CODE_BUTTON_BACK = 3

class FleasTicksAdd : AppCompatActivity() {
    private lateinit var binding: FleasAddBinding
    private lateinit var repository: DatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
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
            repository.addFleas(fleasInfo).subscribe {
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