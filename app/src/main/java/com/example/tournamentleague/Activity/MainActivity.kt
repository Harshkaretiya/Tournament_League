package com.example.tournamentleague.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.tournamentleague.Fragment.HomeFragment
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        replaceFragment(HomeFragment())

        setupBottomNavigationBar()

        setupDrawerLayout()


    }
    private fun setupBottomNavigationBar() {
        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
            }
            true
        }
    }

    private fun setupDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.closeDrawer.setOnClickListener {
            closeDrawer()
        }
        binding.signOut.setOnClickListener {
            var edit1: SharedPreferences.Editor = sharedPreferences.edit()

            edit1.putString("User_Session", "logout")
            edit1.apply()

            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    // Inside MainActivity class
    fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    fun openDrawer(){
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
    fun closeDrawer(){
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

}