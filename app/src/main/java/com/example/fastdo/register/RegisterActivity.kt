package com.example.fastdo.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.fastdo.BaseActivity
import com.example.fastdo.Home
import com.example.fastdo.R
import com.example.fastdo.databinding.ActivityRegisterBinding
import com.example.fastdo.login.LoginActivity

open class RegisterActivity : BaseActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnHere.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        //go to next activity
        binding.btnNext.setOnClickListener {
            registerUser()

        }

        // go to previous activity
        binding.btnPrevious.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.Cities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.chooseCitySpinner.adapter = adapter
        }
        binding.chooseCitySpinner.onItemSelectedListener = this
    }

    private fun registerUser() {
        val pharmacyName = binding.etPharmacyName.text.toString().trim { it <= ' ' }
        val pharmacyManagerName = binding.etPharmacyManagerName.text.toString().trim { it <= ' ' }
        val pharmacyOwnerName = binding.etPharmacyOwnerName.text.toString().trim { it <= ' ' }

        if (validateForm(pharmacyName, pharmacyManagerName, pharmacyOwnerName)) {

            startNextActivity()
        }
    }

    private fun validateForm(
        pharmacyName: String,
        pharmacyManagerName: String,
        pharmacyOwnerName: String,


        ): Boolean {

        return when {

            TextUtils.isEmpty(pharmacyName) -> {
                showErrorToast("ادخل اسم الصيدلية")
                false
            }
            TextUtils.isEmpty(pharmacyManagerName) -> {
                showErrorToast("ادخل اسم مدير الصيدلية")
                false
            }
            TextUtils.isEmpty(pharmacyOwnerName) -> {
                showErrorToast("ادخل اسم مالك الصيدلية")
                false
            }

            else -> true
        }
    }


    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if (position == 0) {
            ArrayAdapter.createFromResource(
                applicationContext,
                R.array.Sohag_Centers,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.chooseCenterSpinner.adapter = adapter
            }
        } else {
            ArrayAdapter.createFromResource(
                applicationContext,
                R.array.Asut_Centers,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.chooseCenterSpinner.adapter = adapter
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    private fun startNextActivity() {
        val intent = Intent(this, IDsActivity::class.java)
        intent.putExtra("pharmacyName", binding.etPharmacyName.text.toString().trim { it <= ' ' })
        intent.putExtra(
            "pharmacyManagerName",
            binding.etPharmacyManagerName.text.toString().trim { it <= ' ' })
        intent.putExtra(
            "pharmacyOwnerName",
            binding.etPharmacyOwnerName.text.toString().trim { it <= ' ' })
        intent.putExtra("city", binding.chooseCitySpinner.selectedItem.toString())
        intent.putExtra("center", binding.chooseCenterSpinner.selectedItem.toString())
        startActivity(intent)
        finish()
    }

    fun userRegisteredSuccess() {

        Toast.makeText(
            this,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        // Hide the progress dialog
        hideProgressDialog()
    }
}