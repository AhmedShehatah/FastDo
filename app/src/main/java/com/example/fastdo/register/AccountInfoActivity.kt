package com.example.fastdo.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.fastdo.BaseActivity
import com.example.fastdo.databinding.ActivityAccountInfoBinding
import com.example.fastdo.databinding.ActivityContactInfoBinding
import com.example.fastdo.databinding.ActivityIDsBinding
import com.example.fastdo.databinding.ActivityRegisterBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.models.Users
import com.example.fastdo.stagnantactivities.StagnantActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityAccountInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPrevious.setOnClickListener {

        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, StagnantActivity::class.java)
            startActivity(intent)
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString().trim { it <= ' ' }
        val password = binding.etPassword.text.toString().trim { it <= ' ' }
        val data = intent.extras
        val pharmacyName = data!!.getString("pharmacyName")
        val pharmacyManagerName = data.getString("pharmacyManagerName")
        val pharmacyOwnerName = data.getString("pharmacyOwnerName")
        val city = data.getString("city")
        val center = data.getString("center")
        val licenceImage = data.getString("licenceImage")
        val commercialImage = data.getString("commercialImage")
        val phoneNumber = data.getString("phoneNumber")
        val telephoneNumber = data.getString("telephoneNumber")
        val exactAddress = data.getString("exactAddress")



        if (validateForm(email, password)) {
            showProgressDialog()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        val fireBaseUser: FirebaseUser = task.result!!.user!!

                        val user = Users(
                            fireBaseUser.uid,
                            pharmacyName!!,
                            pharmacyManagerName!!,
                            pharmacyOwnerName!!,
                            city!!,
                            center!!,
                            licenceImage!!,
                            commercialImage!!,
                           phoneNumber!!.toLong(),
                            telephoneNumber!!.toLong(),
                            exactAddress!!,
                            email,
                            "Empty For Now"


                        )
                        FireStore().registerUser(user)
                        finish()

                    } else {
                        binding.etEmail.setText(task.exception!!.message!!.toString())
                    }
                }
        }
    }

    private fun validateForm(
        email: String,
        password: String,

        ): Boolean {

        return when {
            TextUtils.isEmpty(email) -> {
                showErrorToast("please enter email address")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorToast("please enter password")
                false
            }

            else -> true


        }
    }





}