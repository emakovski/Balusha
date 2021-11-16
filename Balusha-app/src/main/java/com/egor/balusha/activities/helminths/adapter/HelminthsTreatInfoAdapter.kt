package com.egor.balusha.activities.helminths.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.activities.fleasticks.adapter.FleasTicksInfoAdapter
import com.egor.balusha.activities.fleasticks.model.FleasModel
import com.egor.balusha.activities.helminths.model.HelminthsModel
import com.egor.balusha.databinding.FleasItemBinding
import com.egor.balusha.databinding.HelminthsItemBinding
import com.egor.balusha.dbpets.HelminthsInfo
import java.util.*
import kotlin.collections.ArrayList

class HelminthsTreatInfoAdapter(private var interactionListener: OnHelmInteractionListener) :
    RecyclerView.Adapter<HelminthsTreatInfoAdapter.HelmViewHolder>() {

    private var mModelList: List<HelminthsModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelmViewHolder {
        val binding: HelminthsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.helminths_item, parent, false
        )
        return HelmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HelmViewHolder, position: Int) {
        val model = mModelList?.get(position)
        holder.binding.helm = model

        setupListeners(holder, model)
    }

    private fun setupListeners(holder: HelmViewHolder, model: HelminthsModel?) {
        holder.binding.root.setOnClickListener { interactionListener.onItemClicked(model) }

        holder.binding.buttonDeleteHelminthsItem.setOnClickListener { interactionListener.onDeleteClicked(model) }
    }


    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(updatedList: List<HelminthsModel>?) {
        mModelList = updatedList
        notifyDataSetChanged()
    }


    class HelmViewHolder(var binding: HelminthsItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnHelmInteractionListener {
        fun onItemClicked(helmModel: HelminthsModel?)
        fun onDeleteClicked(helmModel: HelminthsModel?)
    }
}