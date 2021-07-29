package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.DatabaseRepository
import com.egor.balusha.adapters.HelminthsTreatInfoAdapter
import com.egor.balusha.databinding.HelminthsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val ADD_HELMINTH_CODE = 4
private const val SHOW_HELMINTH_CODE = 5
private const val RESULT_CODE_BUTTON_BACK = 3


class HelminthTreatList : AppCompatActivity() {
    private lateinit var binding: HelminthsBinding
    private lateinit var adapter: HelminthsTreatInfoAdapter
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HelminthsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setRecyclerSettings()
        setButtonsListener()
        setAdapterListener()
        setNoTreatsVisibility()
    }

    private fun setButtonsListener() {
        binding.fabHelminths.setOnClickListener {
            val intent = Intent(this, HelminthTreatAdd::class.java)
            startActivityForResult(intent, ADD_HELMINTH_CODE)
        }
        binding.backToMenuHelminths.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun setRecyclerSettings() {
        adapter = HelminthsTreatInfoAdapter()
        binding.recyclerHelminths.adapter = adapter
        binding.recyclerHelminths.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //made with coroutines
        activityScope.launch {
            adapter.updateList(repository.getAllHelminthsList().sortedBy { it.id })
            setNoTreatsVisibility()
        }
        //made with rx
//        repository.getAllHelminthsList()
//            .subscribe { list ->
//                adapter.updateList(list.sortedBy { it.helm_date.toLowerCase(Locale.ROOT) })
//                setNoTreatsVisibility()
//            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            //made with coroutines
            activityScope.launch {
                adapter.updateList(repository.getAllHelminthsList().sortedBy { it.id })
                setNoTreatsVisibility()
            }
            //made with rx
//            repository.getAllHelminthsList()
//                .subscribe { list ->
//                    adapter.updateList(list.sortedBy { it.helm_date.toLowerCase(Locale.ROOT)})
//                    setNoTreatsVisibility()
//                }
        }
        setNoTreatsVisibility()
    }

    private fun setNoTreatsVisibility() {
        if (adapter.itemCount != 0) binding.emptyHelminthsList.visibility = View.INVISIBLE
        else binding.emptyHelminthsList.visibility = View.VISIBLE
    }

    private fun setAdapterListener() {
        adapter.onHelminthInfoShowClickListener = {
            val intent = Intent(this, HelminthTreatItem::class.java)
            intent.putExtra("helminthsInfo", it)
            startActivityForResult(intent, SHOW_HELMINTH_CODE)
        }
        adapter.onEditHelminthClickListener = {
            val intent = Intent(this, HelminthTreatEdit::class.java)
            intent.putExtra("helminthsInfo", it)
            startActivityForResult(intent, SHOW_HELMINTH_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}