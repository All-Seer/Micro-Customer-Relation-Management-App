package com.example.phinmaedapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class UpangSecondFloor : AppCompatActivity() {
    lateinit var dialog: MapCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_second_floor)

        hideSystemBars()

        val closeButton: ImageButton = findViewById(R.id.btnBack)
        val btnRoom201: Button = findViewById(R.id.btnRoom201)
        val btnRoom202: Button = findViewById(R.id.btnRoom202)
        val btnRoom203: Button = findViewById(R.id.btnRoom203)
        val btnRoom204: Button = findViewById(R.id.btnRoom204)
        val btnRoom205: Button = findViewById(R.id.btn205)
        val btnRoom206: Button = findViewById(R.id.btnRoom206)
        val btnClinic: Button = findViewById(R.id.btnClinic)
        val btnRegistrar: Button = findViewById(R.id.btnRegistrar)
        val btnCSDL: Button = findViewById(R.id.btnCSDL)

        closeButton.setOnClickListener {
            dismissFragment()
        }

        btnRoom201.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 201",
                imageResId = R.drawable.map_room_201
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom202.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 202",
                imageResId = R.drawable.map_room_202
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom203.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 203",
                imageResId = R.drawable.map_room_203
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom204.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 204",
                imageResId = R.drawable.map_room_204
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom205.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 205",
                imageResId = R.drawable.map_room_205
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom206.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 206",
                imageResId = R.drawable.map_room_206
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnClinic.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Clinic",
                imageResId = R.drawable.map_clinic
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRegistrar.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Registrar",
                imageResId = R.drawable.map_registrar
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnCSDL.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "CSDL",
                imageResId = R.drawable.map_csdl
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