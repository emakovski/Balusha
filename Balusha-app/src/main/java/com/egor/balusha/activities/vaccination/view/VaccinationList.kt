package com.egor.balusha.activities.vaccination.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.R
import com.egor.balusha.activities.vaccination.adapter.VaccineInfoAdapter
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.activities.vaccination.repository.VaccinationRepository
import com.egor.balusha.activities.vaccination.viewmodel.VaccinationListViewModel
import com.egor.balusha.databinding.VaccinationsBinding
import com.egor.balusha.dbpets.VaccineInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val RESULT_CODE_BUTTON_BACK = 3


class VaccinationList : AppCompatActivity(), VaccineInfoAdapter.OnVacInteractionListener {
    private lateinit var binding: VaccinationsBinding
    private lateinit var vacAdapter: VaccineInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = VaccinationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupRecyclerView()
        initViewModel()
    }
    private fun initView() {
        binding.fabVaccination.setOnClickListener {
            openVacCreationActivity()
        }
        binding.backToMenuVaccinations.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun openVacCreationActivity(vacId: Int = 0) {
        val intent = Intent(this, VaccinationInfo::class.java)
        if (vacId != 0)
            intent.putExtra("vacId", vacId)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerVaccines.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        vacAdapter = VaccineInfoAdapter(this)
        binding.recyclerVaccines.adapter = vacAdapter
    }

    private fun initViewModel() {
        val viewModel: VaccinationListViewModel by lazy {
            ViewModelProvider(this).get(VaccinationListViewModel::class.java)
        }
        viewModel.vacData.observe(
            this,
            Observer(this::updateDataSet)
        )
    }

    private fun updateDataSet(updatedList: List<VaccinationModel>?) {
        vacAdapter.updateDataSet(updatedList?.sortedBy { it.dateVac })
        if (vacAdapter.itemCount==0){
            binding.emptyVaccineList.visibility = View.VISIBLE
        } else{
            binding.emptyVaccineList.visibility = View.GONE
        }
    }

    override fun onItemClicked(vacModel: VaccinationModel?) {
        openVacCreationActivity(vacId = vacModel?.id ?: 0)
    }

    override fun onDeleteClicked(vacModel: VaccinationModel?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.remove_item))
            .setMessage(getString(R.string.warning))
            .setPositiveButton("Apply"
            ) { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    val vac = VaccinationRepository.getVacForId(vacModel!!.id!!.toInt())
                    VaccinationRepository.deleteVac(vacId = vacModel.id!!.toInt())

                    withContext(Dispatchers.Main) {
                        Snackbar.make(binding.root, "Vaccination information deleted", Snackbar.LENGTH_LONG)
                            .setAction("Undo") {
                                undoVacDelete(vac!!)
                            }.show()
                    }
                }
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()

    }

    private fun undoVacDelete(vac: VaccineInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            VaccinationRepository.addVac(vac)
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}