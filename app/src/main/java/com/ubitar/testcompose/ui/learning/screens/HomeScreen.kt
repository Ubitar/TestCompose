package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenSnackbar: () -> Unit,
    onOpenSafeDrawing: () -> Unit,
    onOpenNavigation: () -> Unit,
    onOpenModifier: () -> Unit,
    onOpenPager: () -> Unit,
    onOpenState: () -> Unit,
    onOpenViewModel: () -> Unit,
    onOpenVideo: () -> Unit
) {
    LearningScreen(
        title = "Compose 学习入口",
        summary = "这个首页专门用来放可点击的学习入口。MainActivity 现在只负责启动 Compose 应用，真正的界面跳转交给 NavHost 和 AppNavigator 管理。"
    ) {
        block {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                EntryCard(
                    title = "Snackbar 提示示例",
                    description = "直接体验 Compose 里更常见的页面内提示方式，看看它怎么和 Scaffold 配合。",
                    color = Color(0xFFB56576),
                    onClick = onOpenSnackbar
                )
                EntryCard(
                    title = "safeDrawingPadding() 示例",
                    description = "看 edge-to-edge 下为什么需要安全区，以及它和普通 padding 的区别。",
                    color = Color(0xFF2B6F63),
                    onClick = onOpenSafeDrawing
                )
                EntryCard(
                    title = "Compose 页面跳转机制",
                    description = "学习 NavController、NavHost、route，以及它和 XML + Fragment 导航的核心差异。",
                    color = Color(0xFFD94F3D),
                    onClick = onOpenNavigation
                )
                EntryCard(
                    title = "Modifier 是怎么串起来的",
                    description = "理解布局、背景、点击、padding 为什么都长得像链式调用。",
                    color = Color(0xFF26547C),
                    onClick = onOpenModifier
                )
                EntryCard(
                    title = "Compose 版 ViewPager",
                    description = "学习 HorizontalPager、圆点指示器，以及 TabRow 和翻页联动的常见写法。",
                    color = Color(0xFF7A4E2D),
                    onClick = onOpenPager
                )
                EntryCard(
                    title = "状态与重组",
                    description = "认识 remember、mutableStateOf，以及为什么数据变化会自动刷新界面。",
                    color = Color(0xFF9C6644),
                    onClick = onOpenState
                )
                EntryCard(
                    title = "Compose 和 ViewModel 交互",
                    description = "学习如何把状态放进 ViewModel，再让 Compose 通过 collectAsState() 自动更新界面。",
                    color = Color(0xFF3A7D44),
                    onClick = onOpenViewModel
                )
                EntryCard(
                    title = "Compose 播放视频",
                    description = "学习为什么要配合 ExoPlayer 和 PlayerView，以及如何在 Compose 里正确释放播放器。",
                    color = Color(0xFF5C3D8C),
                    onClick = onOpenVideo
                )
            }
        }
    }
}
