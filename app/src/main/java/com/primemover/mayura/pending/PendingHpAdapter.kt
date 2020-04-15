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
import androidx.recyclerview.widget.RecyclerView
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.imageView
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.hpdetails.HpDetailsActivity
import kotlinx.android.synthetic.main.activity_pendinglist.view.*

class PendingHpAdapter(private val context: Context, private var pending: ArrayList<PendingHpResponse>) :
        RecyclerView.Adapter<PendingHpAdapter.PendingHpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingHpViewHolder {

        return PendingHpViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_pendinglist, parent, false), context, pending)

    }

    override fun getItemCount(): Int = pending.size


    override fun onBindViewHolder(holder: PendingHpViewHolder, position: Int) {

        val pendinglist = pending[position]

        holder.view.hp_no_label.text = pendinglist.hp_no
        holder.view.name_label.text = pendinglist.name
        holder.view.mobile_no_label.text = pendinglist.mobile_no
        holder.view.emi_label.text = pendinglist.emi.toString()
        holder.view.amount_label.text = pendinglist.amount
        holder.view.vehicle_no_label.text = pendinglist.vehicle_no
        holder.view.pending_due_label.text = pendinglist.pending_dues
        imageView(context, holder.view.customer_imageView, pendinglist.image)
    }

    class PendingHpViewHolder(val view: View,
                              private val context: Context,
                              private val pending: ArrayList<PendingHpResponse>)
        : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.phone.setOnClickListener(this)
            view.customer_info_cardView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            val dialPhone: PendingHpResponse = pending[adapterPosition]
            when (v!!.id) {
                R.id.phone -> {
                    dial(dialPhone.mobile_no)
                }

                R.id.customer_info_cardView -> {

                    val intent = Intent(context, HpDetailsActivity::class.java)

                    //Sending value to HpDetails Activity
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