package com.primemover.mayura.hpdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.primemover.mayura.R
import com.primemover.mayura.databinding.AllDueItemsBinding

class DueDetailsAdapter(private var dueDetails: ArrayList<DueDetails>)
    : RecyclerView.Adapter<DueDetailsAdapter.DueDetailsViewHolder>() {

    private lateinit var allDueItemsBinding: AllDueItemsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DueDetailsViewHolder {
        allDueItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.all_due_items, parent, false)

        return DueDetailsViewHolder()
    }

    override fun getItemCount(): Int {

        return if (dueDetails.isNullOrEmpty()) {
            0
        } else {
            dueDetails.size
        }

    }

    override fun onBindViewHolder(holder: DueDetailsViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        allDueItemsBinding.textAmount.text = dueDetails[position].amount.toString()
        allDueItemsBinding.textReceiptNumber.text = dueDetails[position].receipt
        allDueItemsBinding.textDueDate.text = dueDetails[position].due_date
    }

    inner class DueDetailsViewHolder : RecyclerView.ViewHolder(allDueItemsBinding.root)
}