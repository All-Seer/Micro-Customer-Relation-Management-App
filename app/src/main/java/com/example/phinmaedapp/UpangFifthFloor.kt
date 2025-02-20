package com.example.phinmaedapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class UpangFifthFloor : AppCompatActivity() {
    lateinit var dialog: MapCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_fifth_floor)

        val closeButton: ImageButton = findViewById(R.id.btnBack)
        closeButton.setOnClickListener {
            dismissFragment()
        }

        hideSystemBars()

        val btnRoom505: Button = findViewById(R.id.btnRoom505)
        val btnRoom506: Button = findViewById(R.id.btnRoom506)
        val btnKitchenLab: Button = findViewById(R.id.btnKitchenLab)
        val btnDrawingLab: Button = findViewById(R.id.btnDrawingLab)
        val btnStudioLab: Button = findViewById(R.id.btnStudioLab)
        val btnCriminologyLab: Button = findViewById(R.id.btnCriminologyLab)
        val btnLibrary: Button = findViewById(R.id.btnLibrary)

        btnRoom505.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 505",
                imageResId = R.drawable.map_room_505
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnRoom506.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Room 506",
                imageResId = R.drawable.map_room_506
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnKitchenLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Kitchen Lab",
                imageResId = R.drawable.map_kitchen_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnDrawingLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Drawing Lab",
                imageResId = R.drawable.map_drawing_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnStudioLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Studio Lab",
                imageResId = R.drawable.map_studio_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnCriminologyLab.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Criminology Lab",
                imageResId = R.drawable.map_criminology_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        btnLibrary.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Library",
                imageResId = R.drawable.map_library
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