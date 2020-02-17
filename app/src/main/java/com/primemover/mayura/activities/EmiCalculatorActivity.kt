package com.primemover.mayura.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.hideSoftKeyBoard
import com.primemover.mayura.databinding.ActivityEmiCalculatorBinding
import kotlinx.android.synthetic.main.activity_emi_calculator.*
import kotlin.math.round

class EmiCalculatorActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityEmiCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_emi_calculator)
        binding.loanAmount.text
        binding.duration.text
        binding.interestRate.text
        binding.calculate.setOnClickListener(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.calculate -> {

                val loanAmount = binding.loanAmount.text.toString()
                val duration = binding.duration.text.toString()
                val interest = binding.interestRate.text.toString()

                if (loanAmount.isEmpty()) {
                    binding.loanAmountTextInputLayout.error = "Loan Amount is required"
                    binding.loanAmountTextInputLayout.requestFocus()
                    return
                }
                if (duration.isEmpty()) {
                    binding.durationTextInputLayout.error = "Duration is required"
                    binding.durationTextInputLayout.requestFocus()
                    return
                }
                if (interest.isEmpty()) {
                    binding.interestTextInputLayout.error = "Interest is required"
                    binding.interestTextInputLayout.requestFocus()
                    return
                }
                hideSoftKeyBoard(this, v)
                totalCard.visibility = View.VISIBLE

                //Calculate principal
                val calculatePrincipal = loanAmount.toFloat() / duration.toFloat()
                binding.principalLabel.text = round(calculatePrincipal).toString()

                //Calculate Interest
                val formula = interest.toFloat() / 100
                val calculateInterest = loanAmount.toFloat() * formula
                binding.interestLabel.text = calculateInterest.toString()

                //Calculate Total
                val total = calculatePrincipal + calculateInterest
                binding.totalLabel.text = round(total).toString()

            }


        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}