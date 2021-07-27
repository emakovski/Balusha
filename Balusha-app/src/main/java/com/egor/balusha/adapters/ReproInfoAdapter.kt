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
import com.egor.balusha.dbpets.ReproInfo
import java.util.*
import kotlin.collections.ArrayList

class ReproInfoAdapter() : RecyclerView.Adapter<ReproInfoAdapter.ReproInfoViewHolder>(),
    Filterable {
    constructor(savedInfo: List<ReproInfo>) : this() {
        reproInfoList = ArrayList(savedInfo)
        reproInfoListForFilter = ArrayList(reproInfoList)
    }

    private var reproInfoList: ArrayList<ReproInfo> = arrayListOf()
    private var reproInfoListForFilter: ArrayList<ReproInfo> = arrayListOf()
    lateinit var onEditReproClickListener: (reproInfo: ReproInfo) -> Unit
    lateinit var onReproInfoShowClickListener: (reproInfo: ReproInfo) -> Unit

    class ReproInfoViewHolder(itemView: View,
                                private val listenerReproEdit: (reproInfo: ReproInfo) -> Unit,
                                private val listenerReproInfo: (reproInfo: ReproInfo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val item: View = itemView.findViewById(R.id.repro_item)
        private val textDateOfBirth: TextView = itemView.findViewById(R.id.date_of_birth_repr_item)
        private val textNumberPuppies: TextView = itemView.findViewById(R.id.number_of_puppies_repr_item)
        private val buttonEditRepr: ImageButton = itemView.findViewById(R.id.button_edit_repr_item)
        fun bind(reproInfo: ReproInfo) {
            textDateOfBirth.text = reproInfo.repro_birth_date
            textNumberPuppies.text = reproInfo.repro_puppies
            item.setOnClickListener {
                listenerReproInfo.invoke(reproInfo)
            }
            buttonEditRepr.setOnClickListener {
                listenerReproEdit.invoke(reproInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReproInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repro_item, parent, false)
        return ReproInfoViewHolder(
            view,
            onEditReproClickListener,
            onReproInfoShowClickListener
        )
    }

    override fun onBindViewHolder(holder: ReproInfoViewHolder, position: Int) {
        holder.bind(reproInfoList[position])
    }

    override fun getItemCount() = reproInfoList.size

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<ReproInfo>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(reproInfoListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase(Locale.ROOT).trim()
                reproInfoListForFilter.forEach {
                    if (it.repro_birth_date.toLowerCase(Locale.ROOT).contains(filterPattern) || it.repro_birth_date.toLowerCase(
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
            reproInfoList.clear()
            reproInfoList.addAll(p1?.values as ArrayList<ReproInfo>);
            notifyDataSetChanged()
        }

    }

    override fun getFilter() = filter


    fun updateList(list: List<ReproInfo>) {
        reproInfoList = ArrayList(list)
        reproInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()
    }
}