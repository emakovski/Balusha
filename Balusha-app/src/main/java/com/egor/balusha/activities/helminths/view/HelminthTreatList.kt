package com.egor.balusha.activities.helminths.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.activities.helminths.adapter.HelminthsTreatInfoAdapter
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.activities.helminths.repository.HelminthsRepository
import com.egor.balusha.activities.helminths.viewmodel.HelminthsListViewModel
import com.egor.balusha.databinding.HelminthsBinding
import com.egor.balusha.dbpets.HelminthsInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HelminthTreatList : AppCompatActivity(), HelminthsTreatInfoAdapter.OnHelmInteractionListener {

    private lateinit var binding: HelminthsBinding
    private lateinit var helmAdapter: HelminthsTreatInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupRecyclerView()
        initViewModel()
    }
    private fun initView() {
        binding.fabHelminths.setOnClickListener {
            openHelmCreationActivity()
        }
        binding.backToMenuHelminths.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun openHelmCreationActivity(helmId: Int = 0) {
        val intent = Intent(this, HelminthTreatInfo::class.java)
        if (helmId != 0)
            intent.putExtra("helmId", helmId)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerHelminths.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        helmAdapter = HelminthsTreatInfoAdapter(this)
        binding.recyclerHelminths.adapter = helmAdapter
    }

    private fun initViewModel() {
        val viewModel: HelminthsListViewModel by lazy {
            ViewModelProvider(this).get(HelminthsListViewModel::class.java)
        }
        viewModel.helmData.observe(
            this,
            Observer(this::updateDataSet)
        )
    }

    private fun updateDataSet(updatedList: List<HelminthsModel>?) {
        helmAdapter.updateDataSet(updatedList)
        if (helmAdapter.itemCount==0){
            binding.emptyHelminthsList.visibility = View.VISIBLE
        } else{
            binding.emptyHelminthsList.visibility = View.GONE
        }
    }

    override fun onItemClicked(helmModel: HelminthsModel?) {
        openHelmCreationActivity(helmId = helmModel?.id ?: 0)
    }

    override fun onDeleteClicked(helmModel: HelminthsModel?) {
        CoroutineScope(Dispatchers.IO).launch {
            val helm = HelminthsRepository.getHelmForId(helmModel!!.id!!.toInt())
            HelminthsRepository.deleteHelm(helmId = helmModel.id!!.toInt())

            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, "Treatment Deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        undoHelmDelete(helm!!)
                    }.show()
            }
        }
    }

    private fun undoHelmDelete(helm: HelminthsInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            HelminthsRepository.addHelm(helm)
        }
    }

    private fun backToPreviousActivity() {
        finish()
    }

}