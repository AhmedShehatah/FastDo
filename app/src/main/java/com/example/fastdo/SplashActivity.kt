package com.example.fastdo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.fastdo.firebase.FireStore
import com.example.fastdo.stagnantactivities.StagnantActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({

            val currentUserID = FireStore().getCurrentUserID()
            if (currentUserID.isNotEmpty()) {
                val intent = Intent(this, StagnantActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }

        }, 2500)
    }
}