package com.example.fastdo.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.fastdo.BaseActivity
import com.example.fastdo.databinding.ActivityContactInfoBinding

class ContactInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityContactInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnJoinNow.setOnClickListener {
            checkValidation()
        }
        binding.btnPrevious.setOnClickListener {

        }
    }

    private fun setAndGetExtras() {
        val data = intent.extras
        val pharmacyName = data!!.getString("pharmacyName")
        val pharmacyManagerName = data.getString("pharmacyManagerName")
        val pharmacyOwnerName = data.getString("pharmacyOwnerName")
        val city = data.getString("city")
        val center = data.getString("center")
        val licenceImage = data.getString("licenceImage")
        val commercialImage = data.getString("commercialImage")
        val intent = Intent(this, AccountInfoActivity::class.java)
        intent.putExtra("pharmacyName", pharmacyName)
        intent.putExtra("pharmacyManagerName", pharmacyManagerName)
        intent.putExtra("pharmacyOwnerName", pharmacyOwnerName)
        intent.putExtra("city", city)
        intent.putExtra("center", center)
        intent.putExtra("licenceImage", licenceImage)
        intent.putExtra("commercialImage", commercialImage)
        intent.putExtra("phoneNumber", binding.etPhoneNumber.text.toString())
        intent.putExtra("telephoneNumber", binding.etTelephoneNumber.text.toString())
        intent.putExtra("exactAddress", binding.etExactAddress.text.toString())
        startActivity(intent)
    }

    private fun checkValidation() {
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val telephoneNumber = binding.etTelephoneNumber.text.toString()
        val exactAddress = binding.etExactAddress.text.toString()

        if (validateForm(phoneNumber, telephoneNumber, exactAddress)) {
            setAndGetExtras()
        }
    }

    private fun validateForm(
        phone: String,
        telephone: String,
        exactAddress: String,

        ): Boolean {

        return when {
            TextUtils.isEmpty(phone) -> {
                showErrorToast("please enter phone number")
                false
            }
            TextUtils.isEmpty(telephone) -> {
                showErrorToast("please enter telephone number")
                false
            }
            TextUtils.isEmpty(exactAddress) -> {
                showErrorToast("please enter address")
                false
            }

            else -> true


        }
    }
}