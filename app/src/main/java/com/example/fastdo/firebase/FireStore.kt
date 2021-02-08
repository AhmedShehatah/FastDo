package com.example.fastdo.firebase

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.example.fastdo.R
import com.example.fastdo.models.Stagnants
import com.example.fastdo.models.Users
import com.example.fastdo.stagnantapdaters.SearchStagnantModel
import com.example.fastdo.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class FireStore {

    private lateinit var mProgressDialog: Dialog
    private val database = FirebaseDatabase.getInstance()
    fun registerUser(userInfo: Users) {
        val mRef = database.getReference(Constants.USERS)
        mRef.child(Constants.DETAILS)
            .child(getCurrentUserID())
            .setValue(userInfo)

    }

    fun searchStagnant(stagnantInfo: SearchStagnantModel) {

        val mRef = database.getReference(Constants.MARKET)
        mRef.child(Constants.DETAILS)
            .push().setValue(stagnantInfo)
    }

    fun storeStagnant(stagnantInfo: Stagnants, context: Context) {
        showProgressDialog(context)
        val mRef = database.getReference(Constants.MYSTAGNANTS)
        mRef.child(Constants.DETAILS).child(getCurrentUserID())
            .push().setValue(stagnantInfo).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideProgressDialog()
                    Toast.makeText(context, "done!", Toast.LENGTH_SHORT).show()
                } else {
                    hideProgressDialog()
                    Toast.makeText(
                        context, "Failed, please try again later " +
                                "or check your internet connection", Toast.LENGTH_SHORT
                    ).show()
                }
            }


    }

    fun requestedStagnantStore(stagnantInfo: SearchStagnantModel, context: Context) {
        showProgressDialog(context)
        val mRef = database.getReference(Constants.REQUESTEDSTAGNANT)
        mRef.child(Constants.DETAILS).child(getCurrentUserID()).push()
            .setValue(stagnantInfo).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideProgressDialog()
                } else {
                    hideProgressDialog()
                    Toast.makeText(
                        context, "Failed, please try again later " +
                                "or check your internet connection", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun stagnantSentForUsStore(stagnantInfo: SearchStagnantModel, id: String, context: Context) {
        showProgressDialog(context)
        val mRef = database.getReference(Constants.SENTFORUS)
        mRef.child(Constants.DETAILS).child(id)
            .push().setValue(stagnantInfo).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideProgressDialog()
                } else {
                    hideProgressDialog()
                    Toast.makeText(
                        context, "Failed, please try again later " +
                                "or check your internet connection", Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID

    }

    private fun showProgressDialog(context: Context) {
        mProgressDialog = Dialog(context)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.show()
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}