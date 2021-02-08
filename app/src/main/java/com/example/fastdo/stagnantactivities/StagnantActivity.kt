package com.example.fastdo.stagnantactivities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.fastdo.BaseActivity
import com.example.fastdo.Home
import com.example.fastdo.ProfileActivity
import com.example.fastdo.R
import com.example.fastdo.databinding.ActivityStagnantBinding
import com.google.firebase.auth.FirebaseAuth

class StagnantActivity : BaseActivity() {

    private lateinit var binding: ActivityStagnantBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStagnantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar()

        val showStagnantFragment = ShowStagnantFragment()
        val addStagnantFragment = AddStagnantFragment()
        val sentStagnantFragment = SentStagnantFragment()
        val requestedStagnantFragment = RequestedStagnantFragment()
        val searchStagnantFragment = SearchStagnantFragment()

        openFragment(addStagnantFragment)
        binding.bottomNavigation.selectedItemId = R.id.addStagnantMenuItem

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.addStagnantMenuItem -> {
                    openFragment(addStagnantFragment)
                    true
                }
                R.id.sentStagnantMenuItem -> {
                    openFragment(sentStagnantFragment)
                    true
                }
                R.id.showStagnantMenuItem -> {
                    openFragment(showStagnantFragment)
                    true
                }
                R.id.requestedStagnantMenuItem -> {
                    openFragment(requestedStagnantFragment)
                    true
                }
                R.id.searchStagnantMenuItem -> {
                    openFragment(searchStagnantFragment)
                    true
                }
                else -> false

            }

        }


    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }


    private fun setSupportActionBar() {
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setIcon(R.drawable.ic_baseline_login_24)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.stagnant, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, Home::class.java))
                finish()
                true
            }
            R.id.profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
