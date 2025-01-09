package com.example.phinmaedapp

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.phinmaedapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var videoView: VideoView
    private var currentPosition: Int = 0
    private var isVideoPlaying: Boolean = false
    private lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navigation Bar Settings
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> Toast.makeText(applicationContext, "Clicked Item Home", Toast.LENGTH_SHORT).show()
                R.id.schools -> Toast.makeText(applicationContext, "Clicked Item Schools", Toast.LENGTH_SHORT).show()
                R.id.about -> Toast.makeText(applicationContext, "Clicked Item About", Toast.LENGTH_SHORT).show()
            }
            true
        }

        // Video Settings
        videoView = binding.phinmaedVideo
        val videoPath = "android.resource://" + packageName + "/" + R.raw.phinmaedvideo
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.setVolume(0f, 0f)
        }
        videoView.start()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onPause() {
        super.onPause()
        if (isVideoPlaying) {
            currentPosition = videoView.currentPosition
            videoView.pause()
            isVideoPlaying = false
        }
    }
    override fun onResume() {
        super.onResume()
        if (!isVideoPlaying) {
            videoView.seekTo(currentPosition)
            videoView.start()
            isVideoPlaying = true
        }
    }
    override fun onStop() {
        super.onStop()
        if (isVideoPlaying) {
            currentPosition = videoView.currentPosition
        }
    }
}