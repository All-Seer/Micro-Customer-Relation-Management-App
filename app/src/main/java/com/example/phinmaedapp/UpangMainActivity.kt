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
import com.google.firebase.auth.FirebaseAuth

class UpangMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val upanghomeFragment = UpangHomeFragment()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private val upangpdFragment = UpangPersonalDetailsFragment()
    private val upangspFragment = UpangSchoolMap()
    private val upangmodalityFragment = UpangModalityFragment()
    private val upangscholarshipFragment = UpangScholarshipFragment()
    private val upangcalendarfragment = UpangCalendarFragment()
    private val upangeventpagefragment = UpangEventPageFragment()
    private val upangstudentmanualFragment = phinma_studentmanual()
    private lateinit var auth: FirebaseAuth


    private lateinit var binding: ActivityUpangMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

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
            R.id.itemSchoolEvents -> setCurrentFragment(upangeventpagefragment)
            R.id.itemSchoolCalendar -> setCurrentFragment(upangcalendarfragment)
            R.id.itemSchoolMap -> setCurrentFragment(upangspFragment)
            R.id.itemModality -> setCurrentFragment(upangmodalityFragment)
            R.id.itemScholar -> setCurrentFragment(upangscholarshipFragment)
            R.id.itemLogOut -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            R.id.itemSchoolManual -> setCurrentFragment(upangstudentmanualFragment)
            else -> setCurrentFragment(upanghomeFragment)
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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