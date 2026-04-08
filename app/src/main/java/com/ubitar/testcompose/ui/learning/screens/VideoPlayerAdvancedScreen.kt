package com.ubitar.testcompose.ui.learning.screens

import android.net.Uri
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView

private const val SAMPLE_VIDEO_URL =
    "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"

@Composable
fun VideoPlayerAdvancedScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "Compose 播放视频",
        summary = "这个版本在基础播放器上继续加了两件事：播放状态显示，以及全屏切换。这样你可以更直观看到 ExoPlayer 的状态怎样回流到 Compose，再由 Compose 控制外层界面。",
        onBack = onBack
    ) {
        section(
            title = "升级后的结构",
            body = "ExoPlayer 仍然负责播放，PlayerView 仍然负责显示画面和控制条。不同的是，这次我们额外监听 isPlaying，并在 Compose 里维护一个 isFullscreen 状态，用来切换普通模式和全屏弹层。"
        )
        block {
            ComposeVideoPlayerCard(videoUrl = SAMPLE_VIDEO_URL)
        }
        section(
            title = "状态为什么能显示出来",
            body = "因为 Player.Listener 会把播放器的变化告诉我们。回调里更新 mutableStateOf 后，Compose 会自动重组，所以页面上的标签和按钮能实时跟着播放器变化。"
        )
        block {
            CodeLikeLine("player.addListener(object : Player.Listener { ... })")
            CodeLikeLine("var isPlaying by remember { mutableStateOf(player.isPlaying) }")
            CodeLikeLine("if (isPlaying) player.pause() else player.play()")
        }
        section(
            title = "全屏切换怎么理解",
            body = "全屏不是重新造一个播放器，而是把同一个 ExoPlayer 接到全屏 Dialog 里的 PlayerView。也就是说，变化的是外层容器，不是播放器核心，所以进度和播放状态会延续。"
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

    var isPlaying by remember(exoPlayer) { mutableStateOf(exoPlayer.isPlaying) }
    var isFullscreen by remember { mutableStateOf(false) }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black)
        ) {
            VideoPlayerSurface(
                exoPlayer = exoPlayer,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .heightIn(min = 220.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TinyTag(
                text = if (isPlaying) "播放中" else "已暂停",
                color = if (isPlaying) Color(0xFF2B6F63) else Color(0xFF9C6644)
            )
            TinyTag(
                text = if (isFullscreen) "全屏中" else "普通模式",
                color = Color(0xFF26547C)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionChip(
                text = if (isPlaying) "暂停" else "播放",
                onClick = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                    } else {
                        exoPlayer.play()
                    }
                },
                color = Color(0xFFD94F3D)
            )
            ActionChip(
                text = if (isFullscreen) "退出全屏" else "进入全屏",
                onClick = { isFullscreen = !isFullscreen },
                color = Color(0xFF203A43)
            )
        }

        Text(
            text = "示例视频源是 Big Buck Bunny 公网 mp4。这个示例重点在于让你看到：播放状态可以同步到 Compose，全屏切换也只是外层 UI 状态变化。",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }

    if (isFullscreen) {
        FullscreenVideoDialog(
            exoPlayer = exoPlayer,
            isPlaying = isPlaying,
            onTogglePlay = {
                if (exoPlayer.isPlaying) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            },
            onDismiss = { isFullscreen = false }
        )
    }
}

@Composable
private fun VideoPlayerSurface(
    exoPlayer: ExoPlayer,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
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

@Composable
private fun FullscreenVideoDialog(
    exoPlayer: ExoPlayer,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    onDismiss: () -> Unit
) {
    BackHandler(onBack = onDismiss)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            VideoPlayerSurface(
                exoPlayer = exoPlayer,
                modifier = Modifier.fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ActionChip(
                    text = if (isPlaying) "暂停" else "播放",
                    onClick = onTogglePlay,
                    color = Color(0xFFD94F3D)
                )
                ActionChip(
                    text = "退出全屏",
                    onClick = onDismiss,
                    color = Color(0xFF203A43)
                )
            }
        }
    }
}

@Composable
private fun ActionChip(
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(color)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
