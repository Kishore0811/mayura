package com.primemover.mayura.pending

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.imageView
import com.primemover.mayura.databinding.AllPendinglistItemBinding
import com.primemover.mayura.hpdetails.HpDetailsActivity

class PendingHpAdapter(private val context: Context, private var pending: PendingHpResponse) :
        RecyclerView.Adapter<PendingHpAdapter.PendingHpViewHolder>() {

    lateinit var allPendinglistItemBinding: AllPendinglistItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PendingHpViewHolder {

        allPendinglistItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.all_pendinglist_item, parent, false)


        return PendingHpViewHolder(allPendinglistItemBinding, context, pending)

    }

    override fun getItemCount(): Int {
        return pending.hppending.size
    }


    override fun onBindViewHolder(holder: PendingHpViewHolder, position: Int) {

        holder.binding.hpNoLabel.text = pending.hppending[position].hp_no
        holder.binding.amountLabel.text = pending.hppending[position].amount
        holder.binding.pendingDueLabel.text = pending.hppending[position].pending_dues
        holder.binding.emiLabel.text = pending.hppending[position].emi.toString()
        holder.binding.nameLabel.text = pending.hppending[position].name
        holder.binding.mobileNoLabel.text = pending.hppending[position].mobile_no
        holder.binding.vehicleNoLabel.text = pending.hppending[position].vehicle_no
        imageView(context, holder.binding.customerImageView, pending.hppending[position].image)
    }

    class PendingHpViewHolder(val binding: AllPendinglistItemBinding,
                              private val context: Context,
                              private val pending: PendingHpResponse)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.phone.setOnClickListener(this)
            binding.customerInfoCardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val dialPhone: PendingHpResponse = pending
            when (v!!.id) {
                R.id.phone -> {
                    dial(dialPhone.hppending[adapterPosition].mobile_no)
                }
                R.id.customer_info_cardView -> {
                    //Toast.makeText(context, "Card clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, HpDetailsActivity::class.java)
                    intent.putExtra("hpId", (pending.hppending[adapterPosition].hp_id))
                    context.startActivity(intent)
                }
            }
        }

        private fun dial(number: String) {

            when {
                number == "" -> {
                    Toast.makeText(context, "Couldn't find number", Toast.LENGTH_SHORT)
                            .show()
                    return
                }
                context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager
                        .PERMISSION_GRANTED -> {
                    val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                    context.startActivity(i)
                }
                else -> ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest
                        .permission.CALL_PHONE), 1)
            }
        }
    }
}