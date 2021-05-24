package com.egor.balusha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.dbpets.VaccineInfo
import java.util.*
import kotlin.collections.ArrayList

class VaccineInfoAdapter() : RecyclerView.Adapter<VaccineInfoAdapter.VaccineInfoViewHolder>(), Filterable {
    constructor(savedInfo: List<VaccineInfo>) : this() {
        vaccineInfoList = ArrayList(savedInfo)
        vaccineInfoListForFilter = ArrayList(vaccineInfoList)
    }

    private var vaccineInfoList: ArrayList<VaccineInfo> = arrayListOf()
    private var vaccineInfoListForFilter: ArrayList<VaccineInfo> = arrayListOf()
    lateinit var onEditVaccineClickListener: (vaccineInfo: VaccineInfo) -> Unit
    lateinit var onVaccineInfoShowClickListener: (vaccineInfo: VaccineInfo) -> Unit

    class VaccineInfoViewHolder(itemView: View,
                            private val listenerVaccineEdit: (vaccineInfo: VaccineInfo) -> Unit,
                                private val listenerVaccineInfo: (vaccineInfo: VaccineInfo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val item: View = itemView.findViewById(R.id.vaccine_item)
        private val textDateOfVaccine: TextView = itemView.findViewById(R.id.date_of_vaccine)
        private val textUsedVaccine: TextView = itemView.findViewById(R.id.vaccine_used)
        private val buttonEditVaccine: ImageButton = itemView.findViewById(R.id.button_edit_vaccine_item)
        fun bind(vaccineInfo: VaccineInfo) {
            textDateOfVaccine.text = vaccineInfo.vaccine_date
            textUsedVaccine.text = vaccineInfo.vaccine_name
            item.setOnClickListener {
                listenerVaccineInfo.invoke(vaccineInfo)
            }
            buttonEditVaccine.setOnClickListener {
                listenerVaccineEdit.invoke(vaccineInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vaccine_item, parent, false)
        return VaccineInfoViewHolder(view, onEditVaccineClickListener, onVaccineInfoShowClickListener)
    }

    override fun onBindViewHolder(holder: VaccineInfoViewHolder, position: Int) {
        holder.bind(vaccineInfoList[position])
    }

    override fun getItemCount() = vaccineInfoList.size

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<VaccineInfo>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(vaccineInfoListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase(Locale.ROOT).trim()
                vaccineInfoListForFilter.forEach {
                    if (it.vaccine_date.toLowerCase(Locale.ROOT).contains(filterPattern) || it.vaccine_name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            vaccineInfoList.clear()
            vaccineInfoList.addAll(p1?.values as ArrayList<VaccineInfo>);
            notifyDataSetChanged()
        }

    }

    override fun getFilter() = filter


    fun updateList(list: List<VaccineInfo>) {
        vaccineInfoList = ArrayList(list)
        vaccineInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()
    }
}