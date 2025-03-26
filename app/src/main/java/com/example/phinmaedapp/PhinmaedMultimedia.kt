package com.example.phinmaedapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.phinmaedapp.databinding.FragmentPhinmaedMultimediaBinding


class PhinmaedMultimedia : Fragment(R.layout.fragment_phinmaed_multimedia) {
    private var _binding: FragmentPhinmaedMultimediaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhinmaedMultimediaBinding.inflate(inflater, container, false)

        // Sample video list
        val videos = listOf(
            VideoItem(
                id = "1",
                title = "PHINMA UPang Hymn",
                url = "https://www.youtube.com/watch?v=WZX8gYbuCbQ",
                thumbnail = null
            ),
            VideoItem(
                id = "2",
                title = "Sasamahan Kita - PHINMA Education",
                url = "https://www.youtube.com/watch?v=G6utWsyb5AY",
                thumbnail = null
            ),


        )

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VideoListScreen(videos = videos)
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

@Composable
fun VideoListScreen(
    videos: List<VideoItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(videos) { video ->
            VideoPlayerItem(
                video = video,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
        }
    }
}

@Composable
fun VideoPlayerItem(
    video: VideoItem,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = ExoPlayer.Builder(ctx).build().apply {
                    setMediaItem(MediaItem.fromUri(video.url))
                    prepare()
                }
                useController = true
            }
        },
        update = { playerView ->
            if (isPlaying) {
                playerView.player?.play()
            } else {
                playerView.player?.pause()
            }
        }
    )

    Box(modifier = modifier) {
        IconButton(
            onClick = { isPlaying = !isPlaying },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                if (isPlaying) painterResource(id = R.drawable.baseline_pause_24) else painterResource(id = R.drawable.baseline_play_arrow_24),
                contentDescription = if (isPlaying) "Pause" else "Play"
            )
        }
    }
}

data class VideoItem(
    val id: String,
    val title: String,
    val url: String,
    val thumbnail: String?
)