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
import com.example.phinmaedapp.databinding.ActivityUpangMainBinding

class UpangMainActivity : AppCompatActivity() {
    private val upanghomeFragment = UpangHomeFragment()
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityUpangMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT


        val upangpdFragment = UpangPersonalDetailsFragment()
        val upangspFragment = UpangSchoolMap()
        val upangmodalityFragment = UpangModalityFragment()
        val upangscholarshipFragment = UpangScholarshipFragment()

        if (savedInstanceState == null) {
            setCurrentFragment(upanghomeFragment)
        }

        toggle = ActionBarDrawerToggle(this, binding.upangdrawerLayout, R.string.open, R.string.close)
        binding.upangdrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.upangnavView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.itemUpanghome -> setCurrentFragment(upanghomeFragment)

                R.id.itemPersonalDetails -> setCurrentFragment(upangpdFragment)

                R.id.itemSchoolMap -> setCurrentFragment(upangspFragment)

                R.id.itemModality -> setCurrentFragment(upangmodalityFragment)

                R.id.itemScholar -> setCurrentFragment(upangscholarshipFragment)

                R.id.itemLogOut -> startActivity(Intent(this, MainActivity::class.java))
            }
            it.isChecked = false
            binding.upangdrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.flFragment)

        if (currentFragment !is UpangHomeFragment){
            setCurrentFragment(upanghomeFragment)
        }else {
            super.onBackPressed()
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
            replace(R.id.upangFragment, fragment).addToBackStack(null)
            commit()
        }
    fun updateActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
}