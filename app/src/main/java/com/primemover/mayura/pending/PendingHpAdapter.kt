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
import androidx.recyclerview.widget.RecyclerView
import com.primemover.mayura.R
import kotlinx.android.synthetic.main.activity_pendinglist.view.*

class PendingHpAdapter(private val context: Context, private var pending : ArrayList<PendingHpResponse>):
        RecyclerView.Adapter<PendingHpAdapter.PendingHpViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingHpViewHolder {

        return PendingHpViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_pendinglist, parent, false),context, pending)

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

    }

    class PendingHpViewHolder(val view: View,
                              private val context: Context,
                              private val pending: ArrayList<PendingHpResponse>)
         : RecyclerView.ViewHolder(view), View.OnClickListener{

         init {
             view.phone.setOnClickListener(this)
         }

        override fun onClick(v: View?) {

            val dialPhone: PendingHpResponse = pending[adapterPosition]
            when(v!!.id){
                R.id.phone ->{
                    dial(dialPhone.mobile_no)
                    }
                }
        }
         private fun dial(number: String){

             when{
                 number == "" ->{
                     Toast.makeText(context, "Couldn't find number", Toast.LENGTH_SHORT)
                             .show()
                     return
                 }
                 context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager
                         .PERMISSION_GRANTED ->{
                     val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                     context.startActivity(i)
                 }
                 else -> ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest
                         .permission.CALL_PHONE),1)
             }
         }
    }
}