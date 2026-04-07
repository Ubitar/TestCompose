package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SafeDrawingPaddingScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "safeDrawingPadding()",
        summary = "它不是普通留白，而是让内容自动避开状态栏、导航栏、刘海和手势区域。你可以把它理解成“系统安全区 padding”。",
        onBack = onBack
    ) {
        section(
            title = "一句话记忆",
            body = "padding(16.dp) 是你自己想留白，safeDrawingPadding() 是系统要求你避开的危险区域。"
        )

        block {
            DemoCard("例子 1: 完全不加", "内容可能直接压到系统栏区域。", Modifier)
            DemoCard("例子 2: 只加 padding(16.dp)", "有设计留白，但不一定真正安全。", Modifier.padding(16.dp))
            DemoCard("例子 3: 只加 safeDrawingPadding()", "会根据当前设备的 WindowInsets 自动调整。", Modifier.safeDrawingPadding())
            DemoCard(
                "例子 4: safeDrawingPadding() + padding(16.dp)",
                "最常见的组合：先安全，再美观。",
                Modifier.safeDrawingPadding().padding(16.dp)
            )
        }

        section(
            title = "你会在什么时候用它",
            body = "全屏页面、沉浸式页面、自定义顶部 Banner、自己画背景到状态栏下面的时候很常用。尤其是打开 edge-to-edge 后，它能减少大量手算状态栏高度的麻烦。"
        )
    }
}

@Composable
private fun DemoCard(
    title: String,
    description: String,
    modifier: Modifier
) {
    SectionCard(title = title, body = description)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color(0xFFE9DDC7), RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF7A6A52), RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0x55008A5A), RoundedCornerShape(20.dp))
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "绿色部分是安全内容区", style = MaterialTheme.typography.titleSmall)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TinyTag("按钮")
                TinyTag("标签", color = Color(0xFFD94F3D))
            }
            Text(text = "这个区域里放文字和交互更稳妥。", style = MaterialTheme.typography.bodySmall)
        }
    }
    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
}
