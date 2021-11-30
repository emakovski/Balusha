package com.egor.balusha.activities.vaccination.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.activities.vaccination.model.VaccinationModel
import com.egor.balusha.databinding.VaccineItemBinding

class VaccineInfoAdapter(private var interactionListener: OnVacInteractionListener) :
    RecyclerView.Adapter<VaccineInfoAdapter.VacViewHolder>() {

    private var mModelList: List<VaccinationModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacViewHolder {
        val binding: VaccineItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.vaccine_item, parent, false
        )
        return VacViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VacViewHolder, position: Int) {
        val model = mModelList?.get(position)
        holder.binding.vac = model

        setupListeners(holder, model)
    }

    private fun setupListeners(holder: VacViewHolder, model: VaccinationModel?) {
        holder.binding.root.setOnClickListener { interactionListener.onItemClicked(model) }

        holder.binding.buttonDeleteVaccineItem.setOnClickListener { interactionListener.onDeleteClicked(model) }
    }


    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(updatedList: List<VaccinationModel>?) {
        mModelList = updatedList
        notifyDataSetChanged()
    }


    class VacViewHolder(var binding: VaccineItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnVacInteractionListener {
        fun onItemClicked(vacModel: VaccinationModel?)
        fun onDeleteClicked(vacModel: VaccinationModel?)
    }
}