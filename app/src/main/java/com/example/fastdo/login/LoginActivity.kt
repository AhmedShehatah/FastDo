package com.example.fastdo.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.fastdo.BaseActivity
import com.example.fastdo.databinding.ActivityLoginBinding
import com.example.fastdo.models.Users
import com.example.fastdo.stagnantactivities.StagnantActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnJoinNow.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.etEmail.text.toString().trim { it <= ' ' }
        val password = binding.etPassword.text.toString().trim { it <= ' ' }


        if (validateForm(email, password)) {
            showProgressDialog()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        startActivity(Intent(this, StagnantActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "please enter email or password correctly",
                            Toast.LENGTH_SHORT
                        ).show()
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