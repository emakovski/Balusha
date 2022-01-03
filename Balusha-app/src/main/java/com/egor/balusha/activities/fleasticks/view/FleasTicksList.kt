package com.egor.balusha.activities.fleasticks.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.R
import com.egor.balusha.activities.fleasticks.adapter.FleasTicksInfoAdapter
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.fleasticks.repository.FleasRepository
import com.egor.balusha.activities.fleasticks.viewmodel.FleasTicksListViewModel
import com.egor.balusha.activities.main.view.MainActivity
import com.egor.balusha.databinding.FleasAndTicksBinding
import com.egor.balusha.dbpets.FleasInfo
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FleasTicksList : AppCompatActivity(), FleasTicksInfoAdapter.OnFleasInteractionListener {
    private lateinit var binding: FleasAndTicksBinding
    private lateinit var fleasAdapter: FleasTicksInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasAndTicksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupRecyclerView()
        initViewModel()
    }

    private fun initView() {
        binding.fabFleas.setOnClickListener {
            openFleasCreationActivity()
        }
        binding.buttonBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun openFleasCreationActivity(fleasId: Int = 0) {
        val intent = Intent(this, FleasTicksInfo::class.java)
        if (fleasId != 0)
            intent.putExtra("fleasId", fleasId)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.recyclerFleas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fleasAdapter = FleasTicksInfoAdapter(this)
        binding.recyclerFleas.adapter = fleasAdapter
    }

    private fun initViewModel() {
        val viewModel: FleasTicksListViewModel by lazy {
            ViewModelProvider(this).get(FleasTicksListViewModel::class.java)
        }
        viewModel.fleasData.observe(
            this,
            Observer(this::updateDataSet)
        )
    }

    private fun updateDataSet(updatedList: List<FleasModel>?) {
        fleasAdapter.updateDataSet(updatedList?.sortedBy { it.dateFleas })
        if (fleasAdapter.itemCount==0){
            binding.emptyFleasList.visibility = View.VISIBLE
        } else{
            binding.emptyFleasList.visibility = View.GONE
        }
    }

    override fun onItemClicked(fleasModel: FleasModel?) {
        openFleasCreationActivity(fleasId = fleasModel?.id ?: 0)
    }

    override fun onDeleteClicked(fleasModel: FleasModel?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.remove_item))
            .setMessage(getString(R.string.warning))
            .setPositiveButton(getString(R.string.apply)
            ) { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    val fleas = FleasRepository.getFleasForId(fleasModel!!.id!!.toInt())
                    FleasRepository.deleteFleas(fleasId = fleasModel.id!!.toInt())

                    withContext(Dispatchers.Main) {
                        Snackbar.make(binding.root, R.string.info_deleted, Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.undo)) {
                                undoFleasDelete(fleas!!)
                            }.show()
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, i -> dialogInterface.cancel() }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun undoFleasDelete(fleas: FleasInfo) {
        CoroutineScope(Dispatchers.IO).launch {
            FleasRepository.addFleas(fleas)
        }
    }
}