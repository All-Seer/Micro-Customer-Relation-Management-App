package com.example.phinmaedapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.phinmaedapp.databinding.ActivityUpangMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class UpangMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private val upanghomeFragment = UpangHomeFragment()
    private lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private val upangpdFragment = UpangPersonalDetailsFragment()
    private val upangspFragment = UpangSchoolMap()
    private val upangmodalityFragment = UpangModalityFragment()
    private val upangscholarshipFragment = UpangScholarshipFragment()
    private val upangeventpagefragment = UpangEventPageFragment()
    private val upangstudentmanualFragment = phinma_studentmanual()
    private val upangAnnouncementsFragment = UpangAnnouncements()
    private lateinit var auth: FirebaseAuth
    private lateinit var notificationHelper: NotificationHelper


    private lateinit var binding: ActivityUpangMainBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpangMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        notificationHelper = NotificationHelper(applicationContext)

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

        checkAdminStatus()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemUpanghome -> setCurrentFragment(upanghomeFragment)
            R.id.itemPersonalDetails -> setCurrentFragment(upangpdFragment)
            R.id.itemSchoolEvents -> setCurrentFragment(upangeventpagefragment)
            R.id.itemAnnouncement -> setCurrentFragment(upangAnnouncementsFragment)
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
        return when (item.itemId) {
            R.id.menu_admin_notification -> {
                showNotificationDialog()
                true
            }

            else -> {
                if (toggle.onOptionsItemSelected(item)) {
                    true
                } else {
                    super.onOptionsItemSelected(item)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        val currentFragment = supportFragmentManager.findFragmentById(R.id.upangFragment)

        if (currentFragment !is UpangHomeFragment) {
            setCurrentFragment(upanghomeFragment)
            navView.setCheckedItem(R.id.itemUpanghome)
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


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.upangtopbarmenu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val isAdmin = auth.currentUser?.email == "admin@admin.com"
        menu.findItem(R.id.menu_admin_notification).isVisible = isAdmin
        return super.onPrepareOptionsMenu(menu)
    }


    private fun checkAdminStatus() {
        val isAdmin = auth.currentUser?.email == "admin@admin.com"
        invalidateOptionsMenu() // This will trigger onPrepareOptionsMenu
    }

    private fun showNotificationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_admin_notification, null)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Send Announcement")
            .setView(dialogView)
            .setPositiveButton("Send") { _, _ ->
                val title = dialogView.findViewById<EditText>(R.id.et_notification_title).text.toString()
                val message = dialogView.findViewById<EditText>(R.id.et_notification_message).text.toString()

                if (title.isBlank() || message.isBlank()) {
                    Toast.makeText(this, "Title and message cannot be empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                sendNotification(title, message)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }

    private fun sendNotification(title: String, message: String) {
        notificationHelper.sendNotificationToAllUsers(title, message) { success, message ->
            runOnUiThread {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    refreshNotificationsIfAvailable()
                }
            }
        }
    }

    private fun refreshNotificationsIfAvailable() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment is UpangAnnouncements) {
                fragment.refreshNotifications()
            }
        }
    }
}

