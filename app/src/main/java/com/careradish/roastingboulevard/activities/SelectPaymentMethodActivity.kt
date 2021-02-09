package com.careradish.roastingboulevard.activities

import  android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.careradish.roastingboulevard.R
import com.careradish.roastingboulevard.classes.PaymentMethod
import com.careradish.roastingboulevard.fragments.DatePickerFragment
import com.careradish.roastingboulevard.tools.App
import com.careradish.roastingboulevard.tools.FirebaseConnection
import kotlinx.android.synthetic.main.activity_select_payment_method.*
import java.text.SimpleDateFormat
import java.util.*


class SelectPaymentMethodActivity : AppCompatActivity() {


    var method = PaymentMethod.Method.Cash
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_payment_method)

        PrepareSpinnerSelectType()

        editTextExpirationDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus)
                showDatePickerDialog()
        }
        editTextExpirationDate.setOnClickListener {
                showDatePickerDialog()
        }
        textViewAmountPayment.text = App.actualDelivery?.amount.toString()+" €"

        buttonFinishPayment.setOnClickListener {
            FinishPayment()
        }
    }

    private fun FinishPayment() {
        var payment = PaymentMethod()
        if (method != PaymentMethod.Method.Cash) {
            payment.creditCardNumber = editTextCardNumber.text.toString().toInt()
            payment.expirationDate = editTextExpirationDate.text.toString()
            payment.cvv = editTextCVV.text.toString().toInt()
        }
        FirebaseConnection.writeDelivery(App.actualDelivery!!)
        App.actualDelivery = null
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        MainActivity.setInvisibleSeeOrderButton()
    }

    private fun PrepareSpinnerSelectType() {

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            PaymentMethod.getArrMethodsString()
        )
        spinnerPaymentMethod.adapter = arrayAdapter
        spinnerPaymentMethod.setSelection(0)
        spinnerPaymentMethod.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    layoutCreditCardData.visibility = View.GONE
                } else {
                    layoutCreditCardData.visibility = View.VISIBLE
                }
                spinnerPaymentMethod.setSelection(position)
                method = PaymentMethod.getArrMethods()[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
        newFragment.dateSet = { dateResult ->
            editTextExpirationDate.setText(dateResult)
        }
    }
}