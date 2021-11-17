package com.egor.balusha.activities.reproduction.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.activities.reproduction.model.ReproductionModel
import com.egor.balusha.databinding.ReproItemBinding

class ReproInfoAdapter(private var interactionListener: OnReproInteractionListener) :
    RecyclerView.Adapter<ReproInfoAdapter.ReproViewHolder>() {

    private var mModelList: List<ReproductionModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReproViewHolder {
        val binding: ReproItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.repro_item, parent, false
        )
        return ReproViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReproViewHolder, position: Int) {
        val model = mModelList?.get(position)
        holder.binding.repro = model

        setupListeners(holder, model)
    }

    private fun setupListeners(holder: ReproViewHolder, model: ReproductionModel?) {
        holder.binding.root.setOnClickListener { interactionListener.onItemClicked(model) }

        holder.binding.buttonDeleteReprItem.setOnClickListener { interactionListener.onDeleteClicked(model) }
    }


    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(updatedList: List<ReproductionModel>?) {
        mModelList = updatedList
        notifyDataSetChanged()
    }


    class ReproViewHolder(var binding: ReproItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnReproInteractionListener {
        fun onItemClicked(reproModel: ReproductionModel?)
        fun onDeleteClicked(reproModel: ReproductionModel?)
    }
}