package com.egor.balusha.activities.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.egor.balusha.R
import com.egor.balusha.databinding.NotificationItemBinding
import com.egor.balusha.activities.main.model.NotifModel

class NotifAdapter(var interactionListener: OnNoteInteractionListener) :
    RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {

    var mModelList: List<NotifModel>? = null

    private var mSelectedViewHolder: NotifViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        var binding: NotificationItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.notification_item, parent, false
        )
        return NotifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val model = mModelList?.get(position)
        holder.binding.note = model

        setupListeners(holder, model)
    }

    private fun setupListeners(holder: NotifViewHolder, model: NotifModel?) {
        holder.binding.root.setOnClickListener { interactionListener.onNotifClicked(model) }

        holder.binding.ivOptionPin.setOnClickListener {
            interactionListener.onNotifPinClicked(model)
            holder.binding.layoutOverlapIcons.visibility = View.GONE
        }
        holder.binding.ivOptionDelete.setOnClickListener {
            interactionListener.onNotifDeleteClicked(model)
            holder.binding.layoutOverlapIcons.visibility = View.GONE
        }
        holder.binding.ivOptionShare.setOnClickListener {
            interactionListener.onNotifShareClicked(model)
            holder.binding.layoutOverlapIcons.visibility = View.GONE
        }

        holder.binding.root.setOnLongClickListener {
            toggleOptionLayout(holder)
            return@setOnLongClickListener true;
        }

        holder.binding.layoutOverlapIcons.setOnClickListener(null)
        holder.binding.layoutOverlapIcons.setOnLongClickListener {
            toggleOptionLayout(holder)
            return@setOnLongClickListener true;
        }

//        TransitionManager.beginDelayedTransition(
//            holder.binding.layoutOverlapIcons,
//            Slide(Gravity.BOTTOM)
//        )
    }

    private fun toggleOptionLayout(holder: NotifViewHolder) {
        if (holder.binding.layoutOverlapIcons.visibility == View.VISIBLE) {
            holder.binding.layoutOverlapIcons.visibility = View.GONE
        } else {
            if (mSelectedViewHolder != null) {
                if (mSelectedViewHolder?.binding?.layoutOverlapIcons?.visibility == View.VISIBLE) {
                    mSelectedViewHolder?.binding?.layoutOverlapIcons?.visibility = View.GONE
                }
            }
            holder.binding.layoutOverlapIcons.visibility = View.VISIBLE
            mSelectedViewHolder = holder
        }
    }

    override fun getItemCount(): Int {
        return mModelList?.size ?: 0
    }

    fun updateDataSet(updatedList: List<NotifModel>?) {
        mModelList = updatedList
        notifyDataSetChanged()
    }

    fun dismissActiveOptionOverlay() {
        if (mSelectedViewHolder != null) {
            toggleOptionLayout(mSelectedViewHolder!!)
        }
    }

    class NotifViewHolder(var binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface OnNoteInteractionListener {
        fun onNotifClicked(notifModel: NotifModel?)
        fun onNotifPinClicked(notifModel: NotifModel?)
        fun onNotifShareClicked(notifModel: NotifModel?)
        fun onNotifDeleteClicked(notifModel: NotifModel?)
    }
}