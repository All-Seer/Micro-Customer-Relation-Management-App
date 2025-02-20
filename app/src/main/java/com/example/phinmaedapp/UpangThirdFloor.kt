package com.example.phinmaedapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class UpangThirdFloor : AppCompatActivity() {
    lateinit var dialog: MapCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_third_floor)

        val closeButton: ImageButton = findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        hideSystemBars()

        val btnRoom301: Button = findViewById(R.id.btnRoom301)
        val btnRoom302: Button = findViewById(R.id.btnRoom302)
        val btnRoom303: Button = findViewById(R.id.btnRoom303)
        val btnRoom304: Button = findViewById(R.id.btnRoom304)
        val btnRoom305: Button = findViewById(R.id.btnRoom305)
        val btnRoom306: Button = findViewById(R.id.btnRoom306)
        val btnCompLab: Button = findViewById(R.id.btnCompLab)
        val btnMacLab: Button = findViewById(R.id.btnMacLab)

        btnRoom301.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 301",
                imageResId = R.drawable.map_room_301
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom302.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 302",
                imageResId = R.drawable.map_room_302
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom303.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 303",
                imageResId = R.drawable.map_room_303
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom304.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 304",
                imageResId = R.drawable.map_room_304
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom305.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 305",
                imageResId = R.drawable.map_room_305
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom306.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 306",
                imageResId = R.drawable.map_room_306
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnCompLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Computer Lab",
                imageResId = R.drawable.map_computer_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnMacLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Mac Lab",
                imageResId = R.drawable.map_mac_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            WindowInsetsControllerCompat(window, window.decorView)
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun dismissFragment() {
        // Remove this fragment
        //parentFragmentManager.beginTransaction().remove(this).commit()
        //this line of code is incorrect because this is an activity and not a fragment
        //so we need to use supportFragmentManager instead of parentFragmentManager
        //also we cannot remove the activity, we can only finish it
        finish()
    }
}