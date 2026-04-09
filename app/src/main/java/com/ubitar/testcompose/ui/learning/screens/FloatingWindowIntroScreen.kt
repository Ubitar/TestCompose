package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FloatingWindowIntroScreen(
    onBack: () -> Unit,
    onOpenControl: () -> Unit
) {
    LearningScreen(
        title = "悬浮窗：拖拽与靠边吸附",
        summary = "这一页用于讲清楚悬浮窗的交互目标和实现思路，然后跳转到控制页去开关与体验。",
        onBack = onBack
    ) {
        section(
            title = "交互目标",
            body = "支持手指拖拽移动，松手后自动吸附到左右边缘，并且始终保持在屏幕可见范围内。"
        )
        section(
            title = "实现要点",
            body = "使用 BoxWithConstraints 获取屏幕尺寸，拖拽时用 Offset 保存位置，拖拽结束时根据中心点判断吸附到左或右。"
        )
        section(
            title = "体验入口",
            body = "进入控制页可以切换悬浮窗开关并直接拖拽体验。"
        )
        block {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TinyTag(text = "练习")
                ActionButton(
                    text = "进入悬浮窗控制页",
                    onClick = onOpenControl,
                    color = Color(0xFF2B6F63)
                )
            }
        }
    }
}
