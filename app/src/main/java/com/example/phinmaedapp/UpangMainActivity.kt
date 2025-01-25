package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.ActivityUpangMainBinding
import com.google.android.material.navigation.NavigationView

class UpangMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val upanghomeFragment = UpangHomeFragment()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private val upangpdFragment = UpangPersonalDetailsFragment()
    private val upangspFragment = UpangSchoolMap()
    private val upangmodalityFragment = UpangModalityFragment()
    private val upangscholarshipFragment = UpangScholarshipFragment()

    private lateinit var binding: ActivityUpangMainBinding
    private lateinit var toggle: ActionBarDrawerToggle // Declare toggle here

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        toolbar = findViewById(R.id.upangtoolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.upangdrawerLayout)
        navView = binding.upangnavView

        // Initialize toggle here
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            setCurrentFragment(upanghomeFragment)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemUpanghome -> setCurrentFragment(upanghomeFragment)
            R.id.itemPersonalDetails -> setCurrentFragment(upangpdFragment)
            R.id.itemSchoolMap -> setCurrentFragment(upangspFragment)
            R.id.itemModality -> setCurrentFragment(upangmodalityFragment)
            R.id.itemScholar -> setCurrentFragment(upangscholarshipFragment)
            R.id.itemLogOut -> startActivity(Intent(this, MainActivity::class.java))
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.upangFragment, fragment).addToBackStack(null)
            commit()
        }
    fun updateActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}