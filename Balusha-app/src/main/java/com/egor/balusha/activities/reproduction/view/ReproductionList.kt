package com.egor.balusha.activities.reproduction.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.R
import com.egor.balusha.activities.reproduction.adapter.ReproInfoAdapter
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.activities.reproduction.repository.ReproductionRepository
import com.egor.balusha.activities.reproduction.viewmodel.ReproductionListViewModel
import com.egor.balusha.databinding.ReproductionBinding
import com.egor.balusha.dbpets.ReproInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val RESULT_CODE_BUTTON_BACK = 3

class ReproductionList : AppCompatActivity(), ReproInfoAdapter.OnReproInteractionListener {
    private lateinit var binding: ReproductionBinding
    private lateinit var reproAdapter: ReproInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupRecyclerView()
        initViewModel()
    }
    private fun initView() {
        binding.fabRepro.setOnClickListener {
            openReproCreationActivity()
        }
        binding.backToMenuRepro.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun openReproCreationActivity(reproId: Int = 0) {
        val intent = Intent(this, ReproductionInfo::class.java)
        if (reproId != 0)
            intent.putExtra("reproId", reproId)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerRepro.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        reproAdapter = ReproInfoAdapter(this)
        binding.recyclerRepro.adapter = reproAdapter
    }

    private fun initViewModel() {
        val viewModel: ReproductionListViewModel by lazy {
            ViewModelProvider(this).get(ReproductionListViewModel::class.java)
        }
        viewModel.reproData.observe(
            this,
            Observer(this::updateDataSet)
        )
    }

    private fun updateDataSet(updatedList: List<ReproductionModel>?) {
        reproAdapter.updateDataSet(updatedList?.sortedBy { it.dateBirthRepro })
        if (reproAdapter.itemCount==0){
            binding.emptyReproList.visibility = View.VISIBLE
        } else{
            binding.emptyReproList.visibility = View.GONE
        }
    }

    override fun onItemClicked(reproModel: ReproductionModel?) {
        openReproCreationActivity(reproId = reproModel?.id ?: 0)
    }

    override fun onDeleteClicked(reproModel: ReproductionModel?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.remove_item))
            .setMessage(getString(R.string.warning))
            .setPositiveButton(getString(R.string.apply)
            ) { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    val repro = ReproductionRepository.getReproForId(reproModel!!.id!!.toInt())
                    ReproductionRepository.deleteRepro(reproId = reproModel.id!!.toInt())

                    withContext(Dispatchers.Main) {
                        Snackbar.make(binding.root, R.string.info_deleted, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.undo)) {
                                undoReproDelete(repro!!)
                            }.show()
                    }
                }
                }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()

    }

    private fun undoReproDelete(repro: ReproInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            ReproductionRepository.addRepro(repro)
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}