package com.example.phinmaedapp

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mFirebaseAnalytics: FirebaseAnalytics
    private val phinmaedhomeFragment = PhinmaedDefaultHome()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        setStatusBarColor(R.color.phinmaPrimary)
        FirebaseApp.initializeApp(this)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
        }

        val phinmaedhomeFragment = PhinmaedDefaultHome()
        val phinmaedaboutFragment = PhinmaedAbout()
        val phinmaedmultimediaFragment = PhinmaedMultimedia()
        val phinmaedschoolsFragment = PhinmaedSchools()

        if (savedInstanceState == null) {
            setCurrentFragment(phinmaedhomeFragment)
        }
        binding.navView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.itemhome -> setCurrentFragment(phinmaedhomeFragment)
                R.id.itemSchools -> setCurrentFragment(phinmaedschoolsFragment)
                R.id.itemAbout -> setCurrentFragment(phinmaedaboutFragment)
                R.id.itemMultimedia -> setCurrentFragment(phinmaedmultimediaFragment)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.flFragment)

        if (currentFragment !is PhinmaedDefaultHome){
            setCurrentFragment(phinmaedhomeFragment)
            binding.navView.selectedItemId = R.id.itemhome
        }else {
            super.onBackPressed()
        }
    }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment).addToBackStack(null)
            commit()
        }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
    private fun setStatusBarColor(colorResId: Int) {
        val color = ContextCompat.getColor(this, colorResId)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }


}


