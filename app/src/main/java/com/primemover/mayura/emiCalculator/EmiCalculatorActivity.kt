package com.primemover.mayura.emiCalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.primemover.mayura.R
import com.primemover.mayura.constants.Utils.hideSoftKeyBoard
import com.primemover.mayura.constants.Utils.toastMessage
import com.primemover.mayura.databinding.ActivityEmiCalculatorBinding
import kotlinx.android.synthetic.main.activity_emi_calculator.*
import kotlin.math.round

class EmiCalculatorActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    lateinit var binding: ActivityEmiCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_emi_calculator)
        binding.loanAmount.addTextChangedListener(this)
        binding.duration.addTextChangedListener(this)
        binding.interestRate.addTextChangedListener(this)
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

                try {

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


                } catch (e: NumberFormatException) {
                    totalCard.visibility = View.GONE

                    toastMessage(this, R.string.empty_fields)

                }
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
        when (s.hashCode()) {

            binding.loanAmount.text.hashCode() -> {
                if (binding.loanAmount.text.isNullOrEmpty()) {
                    binding.loanAmount.error = getString(R.string.loan_error)
                    return

                } else {
                    binding.loanAmount.error = null
                }
            }
            binding.duration.text.hashCode() -> {
                if (binding.duration.text.isNullOrEmpty()) {
                    binding.duration.error = getString(R.string.duration_error)
                    return

                } else {
                    binding.duration.error = null
                }

            }
            binding.interestRate.text.hashCode() -> {
                if (binding.interestRate.text.isNullOrEmpty()) {
                    binding.interestRate.error = getString(R.string.interest_error)
                    return

                } else {
                    binding.interestRate.error = null
                }

            }
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}
