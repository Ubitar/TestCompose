package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenSafeDrawing: () -> Unit,
    onOpenNavigation: () -> Unit,
    onOpenModifier: () -> Unit,
    onOpenState: () -> Unit
) {
    LearningScreen(
        title = "Compose 学习入口",
        summary = "这个首页专门用来放可点击的学习入口。MainActivity 现在只负责启动 Compose 应用，真正的界面跳转交给 NavHost 管理。"
    ) {
        block {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                    title = "状态与重组",
                    description = "认识 remember、mutableStateOf，以及为什么数据变化会自动刷新界面。",
                    color = Color(0xFF9C6644),
                    onClick = onOpenState
                )
            }
        }
    }
}
