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
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.imageView
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.AllPendinglistItemBinding
import com.primemover.mayura.hpdetails.HpDetailsActivity

class PendingHpAdapter(val context: Context, private var pending: ArrayList<PendingHpResponse>) :
        RecyclerView.Adapter<PendingHpAdapter.PendingHpViewHolder>() {

    private lateinit var allPendingListItemBinding: AllPendinglistItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PendingHpViewHolder {

        allPendingListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.all_pendinglist_item, parent, false)

        return PendingHpViewHolder(allPendingListItemBinding, pending)

    }

    override fun getItemCount(): Int = pending.size


    override fun onBindViewHolder(holder: PendingHpViewHolder, position: Int) {

        holder.binding.hpNoLabel.text = pending[position].hp_no
        holder.binding.amountLabel.text = pending[position].amount
        holder.binding.pendingDueLabel.text = pending[position].pending_dues
        holder.binding.emiLabel.text = pending[position].emi.toString()
        holder.binding.nameLabel.text = pending[position].name
        holder.binding.mobileNoLabel.text = pending[position].mobile_no
        holder.binding.vehicleNoLabel.text = pending[position].vehicle_no
        imageView(context, holder.binding.customerImageView, pending[position].image)
    }

    inner class PendingHpViewHolder(val binding: AllPendinglistItemBinding,
                                    private val pending: ArrayList<PendingHpResponse>)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.phone.setOnClickListener(this)
            binding.customerInfoCardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val dialPhone: String = pending[adapterPosition].mobile_no
            when (v!!.id) {
                R.id.phone -> {
                    dial(dialPhone)
                }
                R.id.customer_info_cardView -> {
                    val intent = Intent(context, HpDetailsActivity::class.java)
                    intent.putExtra("hpId", (pending[adapterPosition].hp_id))
                    context.startActivity(intent)
                }
            }
        }

        private fun dial(number: String) {

            when {
                number == "" -> {
                    toastMessage(context, R.string.error_number)
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