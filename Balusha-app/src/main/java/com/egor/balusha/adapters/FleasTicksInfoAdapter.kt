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
import com.egor.balusha.dbpets.FleasInfo
import java.util.*
import kotlin.collections.ArrayList

class FleasTicksInfoAdapter() : RecyclerView.Adapter<FleasTicksInfoAdapter.FleasTicksInfoViewHolder>(),
    Filterable {
    constructor(savedInfo: List<FleasInfo>) : this() {
        fleasInfoList = ArrayList(savedInfo)
        fleasInfoListForFilter = ArrayList(fleasInfoList)
    }

    private var fleasInfoList: ArrayList<FleasInfo> = arrayListOf()
    private var fleasInfoListForFilter: ArrayList<FleasInfo> = arrayListOf()
    lateinit var onEditFleasClickListener: (fleasInfo : FleasInfo) -> Unit
    lateinit var onFleasInfoShowClickListener: (fleasInfo : FleasInfo) -> Unit

    class FleasTicksInfoViewHolder(itemView: View,
                                       private val listenerFleasEdit: (fleasInfo : FleasInfo) -> Unit,
                                       private val listenerFleasInfo: (fleasInfo : FleasInfo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val item: View = itemView.findViewById(R.id.fleas_item)
        private val textDateOfTreatment: TextView = itemView.findViewById(R.id.date_of_fleas)
        private val textUsedTreatment: TextView = itemView.findViewById(R.id.treatment_fleas_used)
        private val buttonEditTreatment: ImageButton = itemView.findViewById(R.id.button_edit_fleas_item)
        fun bind(fleasInfo : FleasInfo) {
            textDateOfTreatment.text = fleasInfo.fleas_date
            textUsedTreatment.text = fleasInfo.fleas_name
            item.setOnClickListener {
                listenerFleasInfo.invoke(fleasInfo)
            }
            buttonEditTreatment.setOnClickListener {
                listenerFleasEdit.invoke(fleasInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FleasTicksInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fleas_item, parent, false)
        return FleasTicksInfoViewHolder(
            view,
            onEditFleasClickListener,
            onFleasInfoShowClickListener
        )
    }

    override fun onBindViewHolder(holder: FleasTicksInfoViewHolder, position: Int) {
        holder.bind(fleasInfoList[position])
    }

    override fun getItemCount() = fleasInfoList.size

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<FleasInfo>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(fleasInfoListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase(Locale.ROOT).trim()
                fleasInfoListForFilter.forEach {
                    if (it.fleas_date.toLowerCase(Locale.ROOT).contains(filterPattern) || it.fleas_name.toLowerCase(
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
            fleasInfoList.clear()
            fleasInfoList.addAll(p1?.values as ArrayList<FleasInfo>);
            notifyDataSetChanged()
        }

    }

    override fun getFilter() = filter


    fun updateList(list: List<FleasInfo>) {
        fleasInfoList = ArrayList(list)
        fleasInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()
    }
}