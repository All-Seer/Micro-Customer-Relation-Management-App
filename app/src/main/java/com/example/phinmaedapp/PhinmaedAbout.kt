package com.example.phinmaedapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.google.android.material.imageview.ShapeableImageView

class PhinmaedAbout : Fragment(R.layout.fragment_phinmaed_about) {
    private val carouselItems = listOf(
        CarouselItem(R.drawable.integrity, "INTEGRITY"),
        CarouselItem(R.drawable.competence, "COMPETENCE"),
        CarouselItem(R.drawable.commitment, "COMMITMENT"),
        CarouselItem(R.drawable.professionalism, "PROFESSIONALISM"),
        CarouselItem(R.drawable.teamwork, "TEAMWORK"),
        CarouselItem(R.drawable.openness, "OPENNESS"),
        CarouselItem(R.drawable.love_of_country, "LOVE OF COUNTRY")
    )

    data class CarouselItem(val imageRes: Int, val title: String)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVideoPlayer(view)
        setupCarousel(view)
        setupSnapBehavior(view)
    }

    private fun setupVideoPlayer(view: View) {
        val videoView = view.findViewById<VideoView>(R.id.Video)
        val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.phinmaaboutvideo

        videoView.setVideoPath(videoPath)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.start()
        }

        videoView.setOnErrorListener { mp, what, extra ->
            Toast.makeText(context, "Error playing video", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun setupCarousel(view: View) {
        val container = view.findViewById<LinearLayout>(R.id.carouselContainer)

        carouselItems.forEach { item ->
            val itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_carousel, container, false)

            itemView.findViewById<ShapeableImageView>(R.id.itemImage).apply {
                setImageResource(item.imageRes)
                elevation = 4f
            }

            itemView.findViewById<TextView>(R.id.itemText).text = item.title

            itemView.setOnClickListener {
                Toast.makeText(context, "PHINMA Value: ${item.title}", Toast.LENGTH_SHORT).show()
            }

            container.addView(itemView)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSnapBehavior(view: View) {
        val scrollView = view.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)

        scrollView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    snapToNearestItem(view)
                    false
                }
                else -> false
            }
        }
    }

    private fun snapToNearestItem(view: View) {
        val scrollView = view.findViewById<HorizontalScrollView>(R.id.horizontalScrollView)
        val container = view.findViewById<LinearLayout>(R.id.carouselContainer)

        if (container.childCount == 0) return

        val firstChild = container.getChildAt(0)
        val itemWidth = firstChild.width +
                (firstChild.layoutParams as ViewGroup.MarginLayoutParams).marginEnd

        val scrollX = scrollView.scrollX
        val nearestItem = (scrollX + itemWidth / 2) / itemWidth
        val targetScroll = nearestItem.coerceIn(0, carouselItems.size - 1) * itemWidth

        scrollView.smoothScrollTo(targetScroll, 0)
    }
}