package com.egor.balusha.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.dbpets.HelminthsInfo
import java.util.*
import kotlin.collections.ArrayList

class HelminthsTreatInfoAdapter() : RecyclerView.Adapter<HelminthsTreatInfoAdapter.HelminthsTreatInfoViewHolder>(),
    Filterable {
    constructor(savedInfo: List<HelminthsInfo>) : this() {
        helminthsInfoList = ArrayList(savedInfo)
        helminthsInfoListForFilter = ArrayList(helminthsInfoList)
    }

    private var helminthsInfoList: ArrayList<HelminthsInfo> = arrayListOf()
    private var helminthsInfoListForFilter: ArrayList<HelminthsInfo> = arrayListOf()
    lateinit var onEditHelminthClickListener: (helminthsInfo : HelminthsInfo) -> Unit
    lateinit var onHelminthInfoShowClickListener: (helminthsInfo : HelminthsInfo) -> Unit

    class HelminthsTreatInfoViewHolder(itemView: View,
                                private val listenerHelminthsEdit: (helminthsInfo : HelminthsInfo) -> Unit,
                                private val listenerHelminthsInfo: (helminthsInfo : HelminthsInfo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val item: View = itemView.findViewById(R.id.helminths_item)
        private val textDateOfTreatment: TextView = itemView.findViewById(R.id.date_of_treatment)
        private val textUsedTreatment: TextView = itemView.findViewById(R.id.treatment_used)
        private val buttonEditTreatment: ImageButton = itemView.findViewById(R.id.button_edit_helminths_item)
        fun bind(helminthsInfo : HelminthsInfo) {
            textDateOfTreatment.text = helminthsInfo.helm_date
            textUsedTreatment.text = helminthsInfo.helm_name
            item.setOnClickListener {
                listenerHelminthsInfo.invoke(helminthsInfo)
            }
            buttonEditTreatment.setOnClickListener {
                listenerHelminthsEdit.invoke(helminthsInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelminthsTreatInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.helminths_item, parent, false)
        return HelminthsTreatInfoViewHolder(
            view,
            onEditHelminthClickListener,
            onHelminthInfoShowClickListener
        )
    }

    override fun onBindViewHolder(holder: HelminthsTreatInfoViewHolder, position: Int) {
        holder.bind(helminthsInfoList[position])
    }

    override fun getItemCount() = helminthsInfoList.size

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<HelminthsInfo>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(helminthsInfoListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase(Locale.ROOT).trim()
                helminthsInfoListForFilter.forEach {
                    if (it.helm_date.toLowerCase(Locale.ROOT).contains(filterPattern) || it.helm_name.toLowerCase(
                            Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            helminthsInfoList.clear()
            helminthsInfoList.addAll(p1?.values as ArrayList<HelminthsInfo>);
            notifyDataSetChanged()
        }

    }

    override fun getFilter() = filter


    fun updateList(list: List<HelminthsInfo>) {
        helminthsInfoList = ArrayList(list)
        helminthsInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()
    }
}