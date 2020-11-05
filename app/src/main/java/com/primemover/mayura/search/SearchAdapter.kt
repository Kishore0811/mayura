package com.primemover.mayura.search

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
import com.primemover.mayura.databinding.AllSearchItemBinding
import com.primemover.mayura.hpdetails.HpDetailsActivity

class SearchAdapter(private val context: Context, private var searchResponse: ArrayList<SearchResponse>)
    : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private lateinit var binding: AllSearchItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.all_search_item, parent, false)

        return SearchViewHolder(binding, searchResponse)
    }

    override fun getItemCount(): Int {
        return if (searchResponse.isNullOrEmpty()) {
            0
        } else {
            searchResponse.size
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.hpNoLabel.text = searchResponse[position].hp_no
        binding.amountLabel.text = searchResponse[position].amount
        binding.pendingDueLabel.text = searchResponse[position].pending_dues
        binding.emiLabel.text = searchResponse[position].emi.toString()
        binding.nameLabel.text = searchResponse[position].name
        binding.mobileNoLabel.text = searchResponse[position].mobile_no
        binding.vehicleNoLabel.text = searchResponse[position].vehicle_no
        binding.ceasingLabel.text = searchResponse[position].cheazing_status
        imageView(context, holder.binding.customerImageView, searchResponse[position].image)
    }

    inner class SearchViewHolder(val binding: AllSearchItemBinding, private val searchResponse: ArrayList<SearchResponse>)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.customerInfoCardView.setOnClickListener(this)
            binding.phone.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val dialPhone: String = searchResponse[adapterPosition].mobile_no
            when (v!!.id) {
                R.id.phone -> {
                    dial(dialPhone)
                }
                R.id.customer_info_cardView -> {
                    val intent = Intent(context, HpDetailsActivity::class.java)
                    intent.putExtra("hpId", (searchResponse[adapterPosition].hp_id))
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
