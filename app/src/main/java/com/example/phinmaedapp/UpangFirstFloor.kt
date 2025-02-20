package com.example.phinmaedapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class UpangFirstFloor : AppCompatActivity() {
    lateinit var dialog: MapCustomDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upang_first_floor)

        hideSystemBars()

        val closeButton: ImageButton = findViewById(R.id.btnBack)
        val canteenButton: Button = findViewById(R.id.btnCanteen)
        val isolationRoomButton: Button = findViewById(R.id.btnIsolationRoom)
        val commercialSpaceLeftButton: Button = findViewById(R.id.btnCommercialSpaceLeft)
        val commercialSpaceRightButton: Button = findViewById(R.id.btnCommercialSpaceRight)
        val hydraulicsLabButton: Button = findViewById(R.id.btnHydraulicsLab)
        val soilMaterialAndTrainingLabButton: Button = findViewById(R.id.btnSoilMaterialAndTrainingLab)
        val incubationRoomButton: Button = findViewById(R.id.btnIncubationRoom)
        val mainEntranceButton: Button = findViewById(R.id.btnMainEntrance)
        val marketingDeptButton: Button = findViewById(R.id.btnMarketingDept)
        val stageButton: Button = findViewById(R.id.btnStage)
        val atriumButton: Button = findViewById(R.id.btnAtrium)

        closeButton.setOnClickListener {
            dismissFragment()
        }

        canteenButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Canteen",
                imageResId = R.drawable.map_canteen
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        isolationRoomButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Isolation Room",
                imageResId = R.drawable.map_isolation_room
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        commercialSpaceLeftButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "MLS Room",
                imageResId = R.drawable.map_commercial_space_left
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        commercialSpaceRightButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Student Loan Room",
                imageResId = R.drawable.map_commercial_space_right
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        hydraulicsLabButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Hydraulics Lab",
                imageResId = R.drawable.map_hydraulics_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        soilMaterialAndTrainingLabButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Soil Material and Training Lab",
                imageResId = R.drawable.map_soil_material_and_training_lab
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        incubationRoomButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Incubation Room",
                imageResId = R.drawable.map_incubation_room
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        mainEntranceButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Main Entrance",
                imageResId = R.drawable.map_main_entrance
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        marketingDeptButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Marketing Department",
                imageResId = R.drawable.map_marketing_dept
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        stageButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Stage",
                imageResId = R.drawable.map_stage
            )
            dialog.show(supportFragmentManager, "customDialog")
        }

        atriumButton.setOnClickListener {
            dialog = MapCustomDialogFragment.newInstance(
                title = "Atrium",
                imageResId = R.drawable.map_stage
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
        finish()
    }
}