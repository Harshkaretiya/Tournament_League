package com.example.tournamentleague.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.tournamentleague.Activity.LoginAndRegistration.LoginActivity
import com.example.tournamentleague.Fragment.HomeFragment
import com.example.tournamentleague.Fragment.NotificationFragment
import com.example.tournamentleague.Fragment.RecentMatchFragment
import com.example.tournamentleague.R
import com.example.tournamentleague.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT,Color.TRANSPARENT))
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,systemBars.bottom)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.navView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavBar)) { v, insets ->
            v.setPadding(0, 0, 0, 0)
            insets
        }


        sharedPreferences = getSharedPreferences("User_Session", Context.MODE_PRIVATE)

        replaceFragment(HomeFragment())

        setupBottomNavigationBar()

        setupDrawerLayout()


    }
    private fun setupBottomNavigationBar() {
        binding.bottomNavBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.recentMatch -> replaceFragment(RecentMatchFragment())
                R.id.notification -> replaceFragment(NotificationFragment())
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
            edit1.clear()
            edit1.apply()
            closeDrawer()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.profileLayout.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,ProfileActivity::class.java))
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment)
            .commit()
    }

    // Inside MainActivity class
    //not used
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

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else {

            val builder = MaterialAlertDialogBuilder(this@MainActivity, R.style.myMaterialDialog)
            builder.setTitle("Are You Sure You Want to Exit ?")

            builder.setPositiveButton("Yes") { dialog, _ ->
                finishAffinity()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val exitDialog = builder.create()
            exitDialog.show()
        }
    }

}