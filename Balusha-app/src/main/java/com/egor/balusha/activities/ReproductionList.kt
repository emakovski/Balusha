package com.egor.balusha.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.egor.balusha.util.DatabaseRepository
import com.egor.balusha.adapters.ReproInfoAdapter
import com.egor.balusha.databinding.ReproductionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val ADD_REPRO_CODE = 4
private const val SHOW_REPRO_CODE = 5
private const val RESULT_CODE_BUTTON_BACK = 3


class ReproductionList : AppCompatActivity() {
    private lateinit var binding: ReproductionBinding
    private lateinit var adapter: ReproInfoAdapter
    private lateinit var repository: DatabaseRepository
    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReproductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityScope = CoroutineScope(Dispatchers.Main + Job())
        repository = DatabaseRepository(activityScope)
        setRecyclerSettings()
        setButtonsListener()
        setAdapterListener()
        setNoReproVisibility()
    }

    private fun setButtonsListener() {
        binding.fabRepro.setOnClickListener {
            val intent = Intent(this, ReproductionAdd::class.java)
            startActivityForResult(intent, ADD_REPRO_CODE)
        }
        binding.backToMenuRepro.setOnClickListener {
            backToPreviousActivity()
        }
    }

    private fun setRecyclerSettings() {
        adapter = ReproInfoAdapter()
        binding.recyclerRepro.adapter = adapter
        binding.recyclerRepro.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //made with coroutines
        activityScope.launch {
            adapter.updateList(repository.getAllReproList().sortedBy { it.id })
            setNoReproVisibility()
        }
        //made with rx
//        repository.getAllReproList()
//            .subscribe { list ->
//                adapter.updateList(list.sortedBy { it.repro_birth_date.toLowerCase(Locale.ROOT) })
//                setNoReproVisibility()
//            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            //made with coroutines
            activityScope.launch {
                adapter.updateList(repository.getAllReproList().sortedBy { it.id })
                setNoReproVisibility()
            }
            //made with rx
//            repository.getAllReproList()
//                .subscribe { list ->
//                    adapter.updateList(list.sortedBy { it.repro_birth_date.toLowerCase(Locale.ROOT)})
//                    setNoReproVisibility()
//                }
        }
        setNoReproVisibility()
    }

    private fun setNoReproVisibility() {
        if (adapter.itemCount != 0) binding.emptyReproList.visibility = View.INVISIBLE
        else binding.emptyReproList.visibility = View.VISIBLE
    }

    private fun setAdapterListener() {
        adapter.onReproInfoShowClickListener = {
            val intent = Intent(this, ReproductionItem::class.java)
            intent.putExtra("reproInfo", it)
            startActivityForResult(intent, SHOW_REPRO_CODE)
        }
        adapter.onEditReproClickListener = {
            val intent = Intent(this, ReproductionEdit::class.java)
            intent.putExtra("reproInfo", it)
            startActivityForResult(intent, SHOW_REPRO_CODE)
        }
    }
    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }
}