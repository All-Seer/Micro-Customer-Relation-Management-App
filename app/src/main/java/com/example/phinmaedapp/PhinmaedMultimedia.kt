package com.example.phinmaedapp


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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

        val videos = listOf(
            VideoItem(
                id = "1",
                title = "PHINMA UPang Hymn",
                url = "android.resource://${requireContext().packageName}/${R.raw.phinmaupangvideo}",
                thumbnail = R.drawable.upangfacility4,
                duration = "3:45"
            ),
            VideoItem(
                id = "2",
                title = "Sasamahan Kita",
                url = "android.resource://${requireContext().packageName}/${R.raw.phinmaedvideo}",
                thumbnail = R.drawable.upangfacility2,
                duration = "4:20"
            )
        )

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                VideoListScreen(
                    videos = videos,
                    onVideoSelected = { video ->
                        VideoPlayerDialogFragment.newInstance(video.url)
                            .show(parentFragmentManager, "VideoPlayerDialog")
                    }
                )
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
    onVideoSelected: (VideoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(videos) { video ->
            VideoListItem(
                video = video,
                onVideoSelected = onVideoSelected,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun VideoListItem(
    video: VideoItem,
    onVideoSelected: (VideoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onVideoSelected(video) },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = video.thumbnail),
                contentDescription = "Thumbnail",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = video.duration,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                painterResource(R.drawable.baseline_play_arrow_24),
                contentDescription = "Play",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

data class VideoItem(
    val id: String,
    val title: String,
    val url: String,
    @DrawableRes val thumbnail: Int,
    val duration: String
)