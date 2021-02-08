package com.example.fastdo.register

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fastdo.databinding.ActivityIDsBinding

class IDsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIDsBinding
    private var checkedPermission = false
    private lateinit var intentForResult: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIDsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intentForResult = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        binding.btnUploadLicencePic.setOnClickListener {
            checkPermissionRequested(1)
        }
        binding.btnUploadCommercialRecordPic.setOnClickListener {
            checkPermissionRequested(2)
        }
        binding.btnJoinNow.setOnClickListener {
            if (binding.ivUploadedLicencePic.drawable != null
                && binding.ivUploadedCommercialRecordPic.drawable != null
            ) {
                setAndGetExtras()
            } else {
                Toast.makeText(this, "please attach photos!", Toast.LENGTH_SHORT).show()
            }

        }
        binding.btnPrevious.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkPermissionRequested(requestCode: Int) {
        if (checkedPermission)
            startActivityForResult(intentForResult, requestCode)
        else
            requestStoragePermission()
    }

    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,

                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
       checkedPermission = true
        } else {
            checkedPermission = true
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                try {
                    if (data!!.data != null) {
                        binding.ivUploadedLicencePic.setImageURI(data.data)

                    }
                } catch (ex: Exception) {
                }

            } else {
                try {
                    if (data!!.data != null) {
                        binding.ivUploadedCommercialRecordPic.setImageURI(data.data)
                    }
                } catch (ex: Exception) {
                }
            }
        }
    }

    private fun setAndGetExtras() {
        val data = intent.extras
        val pharmacyName = data!!.getString("pharmacyName")
        val pharmacyManagerName = data.getString("pharmacyManagerName")
        val pharmacyOwnerName = data.getString("pharmacyOwnerName")
        val city = data.getString("city")
        val center = data.getString("center")
        val intent = Intent(this, ContactInfoActivity::class.java)
        intent.putExtra("pharmacyName", pharmacyName)
        intent.putExtra("pharmacyManagerName", pharmacyManagerName)
        intent.putExtra("pharmacyOwnerName", pharmacyOwnerName)
        intent.putExtra("city", city)
        intent.putExtra("center", center)
        intent.putExtra("licenceImage", binding.ivUploadedLicencePic.toString())
        intent.putExtra("commercialImage", binding.ivUploadedCommercialRecordPic.toString())
        startActivity(intent)
    }
}