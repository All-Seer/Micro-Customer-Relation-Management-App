package com.example.phinmaedapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class UpangFourthFloor : AppCompatActivity() {
    lateinit var dialog: MapCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_fourth_floor)

        val closeButton: ImageButton = findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        hideSystemBars()

        val btnChemLab: Button = findViewById(R.id.btnChemLab)
        val btnPhysicsLab: Button = findViewById(R.id.btnPhysicsLab)
        val btnRoom401: Button = findViewById(R.id.btnRoom401)
        val btnRoom402: Button = findViewById(R.id.btnRoom402)
        val btnRoom403: Button = findViewById(R.id.btnRoom403)
        val btnRoom404: Button = findViewById(R.id.btnRoom404)
        val btnTertiaryFacultyRoom: Button = findViewById(R.id.btnTertiaryFacultyRoom)
        val btnSHSFacultyRoom: Button = findViewById(R.id.btnSHSFacultyRoom)

        btnChemLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Chemistry Lab",
                imageResId = R.drawable.map_chemistry_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnPhysicsLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Physics Lab",
                imageResId = R.drawable.map_physics_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom401.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 401",
                imageResId = R.drawable.map_room_401
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom402.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 402",
                imageResId = R.drawable.map_room_402
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom403.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 403",
                imageResId = R.drawable.map_room_403
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom404.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 404",
                imageResId = R.drawable.map_room_404
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnTertiaryFacultyRoom.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Tertiary Faculty Room",
                imageResId = R.drawable.map_tertiary_faculty_room
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnSHSFacultyRoom.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "SHS Faculty Room",
                imageResId = R.drawable.map_shs_faculty_room
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