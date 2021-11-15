package com.egor.balusha.activities.fleasticks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.databinding.FleasItemBinding

class FleasTicksInfoAdapter(var interactionListener: OnNoteInteractionListener) :
    RecyclerView.Adapter<FleasTicksInfoAdapter.FleasViewHolder>() {

    var mModelList: List<FleasModel>? = null

    private var mSelectedViewHolder: FleasViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FleasViewHolder {
        var binding: FleasItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fleas_item, parent, false
        )
        return FleasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FleasViewHolder, position: Int) {
        val model = mModelList?.get(position)
        holder.binding.fleas = model

        setupListeners(holder, model)
    }

    private fun setupListeners(holder: FleasViewHolder, model: FleasModel?) {
        holder.binding.root.setOnClickListener { interactionListener.onItemClicked(model) }

        holder.binding.buttonDeleteFleasItem.setOnClickListener { interactionListener.onDeleteClicked(model) }
    }


    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    fun updateDataSet(updatedList: List<FleasModel>?) {
        mModelList = updatedList
        notifyDataSetChanged()
    }


    class FleasViewHolder(var binding: FleasItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnNoteInteractionListener {
        fun onItemClicked(fleasModel: FleasModel?)
        fun onDeleteClicked(fleasModel: FleasModel?)
    }
}