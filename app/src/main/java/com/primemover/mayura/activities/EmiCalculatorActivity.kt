package com.primemover.mayura.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.hideSoftKeyBoard
import com.primemover.mayura.databinding.ActivityEmiCalculatorBinding
import kotlinx.android.synthetic.main.activity_emi_calculator.*
import kotlinx.android.synthetic.main.activity_pendinglist.*
import kotlin.math.round

class EmiCalculatorActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
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

                when {
                    loanAmount.isEmpty() -> {
                        binding.loanAmountTextInputLayout.error = "Loan Amount is required"
                        binding.loanAmountTextInputLayout.requestFocus()
                        return
                    }
                    duration.isEmpty() -> {
                        binding.durationTextInputLayout.error = "Duration is required"
                        binding.durationTextInputLayout.requestFocus()
                        return
                    }
                    interest.isEmpty() -> {
                        binding.interestTextInputLayout.error = "Interest is required"
                        binding.interestTextInputLayout.requestFocus()
                        return
                    }
                    else -> {
                        binding.durationTextInputLayout.error = null
                        binding.loanAmountTextInputLayout.error = null
                        binding.interestTextInputLayout.error = null
                    }
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


    override fun afterTextChanged(s: Editable?) {
        when (view.id) {
            R.id.loanAmount_text_input_layout -> loanAmount_text_input_layout.error = null
            R.id.duration_text_input_layout -> duration_text_input_layout.error = null
            R.id.interest_text_input_layout -> interest_text_input_layout.error = null
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
