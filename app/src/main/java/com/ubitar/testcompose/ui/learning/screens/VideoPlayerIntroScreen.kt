package com.ubitar.testcompose.ui.learning.screens

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

private const val SAMPLE_VIDEO_URL =
    "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"

@Composable
fun VideoPlayerIntroScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "Compose 播放视频",
        summary = "Compose 自己没有内建视频播放器组件，实际项目里通常会把 Media3 ExoPlayer 作为播放器核心，再通过 AndroidView 把 PlayerView 放进 Compose。"
        ,
        onBack = onBack
    ) {
        section(
            title = "推荐组合",
            body = "ExoPlayer 负责解码、缓冲、播放控制；PlayerView 负责控制条、手势和视频画面承载；Compose 负责页面状态和布局。这样比自己从零拼 SurfaceView 更稳。"
        )
        block {
            ComposeVideoPlayerCard(videoUrl = SAMPLE_VIDEO_URL)
        }
        section(
            title = "这个示例想说明什么",
            body = "remember(videoUrl) 用来避免重组时反复创建播放器；DisposableEffect 用来在页面离开时 release()；AndroidView 则是 Compose 和传统 Android View 的桥。"
        )
        block {
            CodeLikeLine("val player = remember(url) { ExoPlayer.Builder(context).build() }")
            CodeLikeLine("DisposableEffect(player) { onDispose { player.release() } }")
            CodeLikeLine("AndroidView(factory = { PlayerView(it).apply { player = exoPlayer } })")
        }
        section(
            title = "和 XML 时代的差异",
            body = "以前你多半在 Activity 或 Fragment 里 findViewById<PlayerView>() 再绑定 player。Compose 里仍然可以复用 PlayerView，但它的创建和销毁会被包在一个可组合函数里，生命周期更集中。"
        )
        section(
            title = "实际项目里的下一步",
            body = "如果你后面想继续扩展，可以再加全屏切换、封面图、播放状态显示、错误重试，或者把 player 提升到 ViewModel 管理，以便横竖屏或页面切换后保留进度。"
        )
    }
}

@Composable
private fun ComposeVideoPlayerCard(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember(videoUrl) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            repeatMode = Player.REPEAT_MODE_ONE
            playWhenReady = true
            prepare()
        }
    }

    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black)
        ) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .heightIn(min = 220.dp),
                factory = { viewContext ->
                    PlayerView(viewContext).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        useController = true
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                        player = exoPlayer
                    }
                },
                update = { playerView ->
                    playerView.player = exoPlayer
                }
            )
        }

        Text(
            text = "示例视频源：Big Buck Bunny 公网 mp4。首次进入页面会自动播放，离开页面会自动释放播放器。",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}
