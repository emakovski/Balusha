package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.adapters.VaccineInfoAdapter
import com.egor.balusha.databinding.VaccinationsBinding
import java.util.*

private const val ADD_VACCINATION_CODE = 4
private const val SHOW_VACCINATION_CODE = 5
private const val RESULT_CODE_BUTTON_BACK = 3


class VaccinationList : AppCompatActivity() {
    private lateinit var binding: VaccinationsBinding
    private lateinit var adapter: VaccineInfoAdapter
    private lateinit var repository: DatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccinationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repository = DatabaseRepository()
        setRecyclerSettings()
        setButtonsListener()
        setAdapterListener()
        setNoVaccinationsVisibility()
    }

    private fun setButtonsListener() {
        binding.fabVaccination.setOnClickListener {
            val intent = Intent(this, VaccinationAdd::class.java)
            startActivityForResult(intent, ADD_VACCINATION_CODE)
        }
        binding.backToMenuVaccinations.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun setRecyclerSettings() {
        adapter = VaccineInfoAdapter()
        binding.recyclerVaccines.adapter = adapter
        binding.recyclerVaccines.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repository.getAllVaccineList()
            .subscribe { list ->
                adapter.updateList(list.sortedBy { it.vaccine_date.toLowerCase(Locale.ROOT) })
                setNoVaccinationsVisibility()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            repository.getAllVaccineList()
                .subscribe { list ->
                    adapter.updateList(list.sortedBy { it.vaccine_date.toLowerCase(Locale.ROOT)})
                    setNoVaccinationsVisibility()
                }
        }
        setNoVaccinationsVisibility()
    }

    private fun setNoVaccinationsVisibility() {
        if (adapter.itemCount != 0) binding.emptyVaccineList.visibility = View.INVISIBLE
        else binding.emptyVaccineList.visibility = View.VISIBLE
    }

    private fun setAdapterListener() {
        adapter.onVaccineInfoShowClickListener = {
            val intent = Intent(this, VaccinationItem::class.java)
            intent.putExtra("vaccineInfo", it)
            startActivityForResult(intent, SHOW_VACCINATION_CODE)
        }
        adapter.onEditVaccineClickListener = {
            val intent = Intent(this, VaccinationEdit::class.java)
            intent.putExtra("vaccineInfo", it)
            startActivityForResult(intent, SHOW_VACCINATION_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}