package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.adapters.FleasTicksInfoAdapter
import com.egor.balusha.databinding.FleasAndTicksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val ADD_FLEAS_CODE = 4
private const val SHOW_FLEAS_CODE = 5
private const val RESULT_CODE_BUTTON_BACK = 3


class FleasTicksList : AppCompatActivity() {
    private lateinit var binding: FleasAndTicksBinding
    private lateinit var adapter: FleasTicksInfoAdapter
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FleasAndTicksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setRecyclerSettings()
        setButtonsListener()
        setAdapterListener()
        setNoTreatsVisibility()
    }

    private fun setButtonsListener() {
        binding.fabFleas.setOnClickListener {
            val intent = Intent(this, FleasTicksAdd::class.java)
            startActivityForResult(intent, ADD_FLEAS_CODE)
        }
        binding.backToMenuFleas.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun setRecyclerSettings() {
        adapter = FleasTicksInfoAdapter()
        binding.recyclerFleas.adapter = adapter
        binding.recyclerFleas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //made with coroutines
        activityScope.launch {
            adapter.updateList(repository.getAllFleasList().sortedBy { it.fleas_date.toLowerCase() })
            setNoTreatsVisibility()
        }
        //made with rx
//        repository.getAllFleasList()
//            .subscribe { list ->
//                adapter.updateList(list.sortedBy { it.fleas_date.toLowerCase(Locale.ROOT) })
//                setNoTreatsVisibility()
//            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            //made with coroutines
            activityScope.launch {
                adapter.updateList(repository.getAllFleasList().sortedBy { it.fleas_date.toLowerCase() })
                setNoTreatsVisibility()
            }
            //made with rx
//            repository.getAllFleasList()
//                .subscribe { list ->
//                    adapter.updateList(list.sortedBy { it.fleas_date.toLowerCase(Locale.ROOT)})
//                    setNoTreatsVisibility()
//                }
        }
        setNoTreatsVisibility()
    }

    private fun setNoTreatsVisibility() {
        if (adapter.itemCount != 0) binding.emptyFleasList.visibility = View.INVISIBLE
        else binding.emptyFleasList.visibility = View.VISIBLE
    }

    private fun setAdapterListener() {
        adapter.onFleasInfoShowClickListener = {
            val intent = Intent(this, FleasTicksItem::class.java)
            intent.putExtra("fleasInfo", it)
            startActivityForResult(intent, SHOW_FLEAS_CODE)
        }
        adapter.onEditFleasClickListener = {
            val intent = Intent(this, FleasTicksEdit::class.java)
            intent.putExtra("fleasInfo", it)
            startActivityForResult(intent, SHOW_FLEAS_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}