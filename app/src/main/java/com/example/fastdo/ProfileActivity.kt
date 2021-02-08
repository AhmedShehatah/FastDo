package com.example.fastdo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import com.example.fastdo.databinding.ActivityProfileBinding
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.stagnantactivities.StagnantActivity
import com.example.fastdo.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showProgressDialog()
        val mRef = FirebaseDatabase.getInstance()
            .getReference(Constants.USERS).child(Constants.DETAILS)
            .child(FireStore().getCurrentUserID())
        var email = ""
        var phoneNumber = ""
        var pharmacyName = ""
        var pharmacyManagerName = ""
        var pharmacyOwnerName = ""
        mRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    when (data.key) {
                        "email" -> email = data.value.toString()
                        "phoneNumber" -> phoneNumber = data.value.toString()
                        "pharmacyName" -> pharmacyName = data.value.toString()
                        "pharmacyManagerName" -> pharmacyManagerName = data.value.toString()
                        "pharmacyOwnerName" -> pharmacyOwnerName = data.value.toString()
                    }
                }
                binding.etEmail.setText(email)
                binding.etPhoneNumber.setText(phoneNumber)
                binding.etPharmacyName.setText(pharmacyName)
                binding.etPharmacyManagerName.setText(pharmacyManagerName)
                binding.etPharmacyOwnerName.setText(pharmacyOwnerName)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


        mRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) hideProgressDialog()
            else {
                hideProgressDialog()
                showErrorToast("Failed to load your information please try again " +
                        "or check your internet connection")
                finish()
            }
        }

        binding.btnConfirm.setOnClickListener {
            val newEmail = binding.etEmail.text.toString()
            val newPhoneNumber = binding.etPhoneNumber.text.toString()
            val newPharmacyName = binding.etPharmacyName.text.toString()
            val newPharmacyManagerName = binding.etPharmacyManagerName.text.toString()
            val newPharmacyOwnerName = binding.etPharmacyOwnerName.text.toString()

            if (email != newEmail ||
                phoneNumber != newPhoneNumber
                || pharmacyName != newPharmacyName
                || pharmacyManagerName != newPharmacyManagerName
                || pharmacyOwnerName != newPharmacyOwnerName

            ) {
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Confirm")
                dialog.setMessage("هل تريد تغيير معلوماتك الشخصية ؟")
                dialog.setPositiveButton("yes") { _, _ ->
                    showProgressDialog()
                    mRef.child("email").setValue(newEmail)
                    mRef.child("phoneNumber").setValue(newPhoneNumber)
                    mRef.child("pharmacyName").setValue(newPharmacyName)
                    mRef.child("pharmacyManagerName").setValue(newPharmacyManagerName)
                    mRef.child("pharmacyOwnerName").setValue(newPharmacyOwnerName)
                    mRef.get().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            hideProgressDialog()
                            startActivity(
                                Intent(
                                    this@ProfileActivity,
                                    StagnantActivity::class.java
                                )
                            )
                            finish()
                        } else {
                            showErrorToast("Failed")
                        }
                    }
                }
                dialog.setNegativeButton("No") { _, _ ->
                    binding.etPhoneNumber.setText(phoneNumber)
                    binding.etPharmacyName.setText(pharmacyName)
                    binding.etPharmacyManagerName.setText(pharmacyManagerName)
                    binding.etPharmacyOwnerName.setText(pharmacyOwnerName)
                }
                dialog.show()
            } else {
                finish()
            }

        }
    }
}