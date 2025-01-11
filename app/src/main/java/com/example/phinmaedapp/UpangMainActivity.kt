package com.example.phinmaedapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import com.example.phinmaedapp.databinding.ActivityUpangMainBinding

class UpangMainActivity : AppCompatActivity() {
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


        toggle = ActionBarDrawerToggle(this, binding.upangdrawerLayout, R.string.open, R.string.close)
        binding.upangdrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.upangnavView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.itemUpanghome -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Home",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemPersonalDetails -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Item Personal Details",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.itemAddressDetails -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Address Details",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemCourseDetails -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Course Details",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemPhotoSignature -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Photo Signature",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemEnlistedSubjects -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Enlisted Subjects",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemModality -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Modality Help Page",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemScholar -> Toast.makeText(
                    applicationContext,
                    "Clicked Item Scholarship Page",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.itemLogOut -> startActivity(Intent(this, MainActivity::class.java))
            }
            it.isChecked = false
            binding.upangdrawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}