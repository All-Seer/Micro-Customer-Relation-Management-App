package com.example.phinmaedapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        val phinmaedhomeFragment = PhinmaedDefaultHome()
        val phinmaedaboutFragment = PhinmaedAbout()
        val phinmaedcommunityFragment = PhinmaedCommunity()

        setCurrentFragment(phinmaedhomeFragment)

        //Navigation Bar Settings
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.navView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.itemhome -> setCurrentFragment(phinmaedhomeFragment)

                R.id.itemUpang -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                R.id.itemAbout -> setCurrentFragment(phinmaedaboutFragment)

                R.id.itemCommunity -> setCurrentFragment(phinmaedcommunityFragment)
            }
            it.isChecked = false
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }


}
