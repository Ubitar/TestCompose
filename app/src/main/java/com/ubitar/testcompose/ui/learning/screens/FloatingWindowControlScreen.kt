package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun FloatingWindowControlScreen(onBack: () -> Unit) {
    var enabled by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            BackPill(onBack = onBack)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "悬浮窗开关",
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = enabled,
                    onCheckedChange = { enabled = it }
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "拖拽圆形悬浮窗可移动位置。松手后会自动吸附到左右边缘，并保持在屏幕范围内。",
                color = Color(0xFF555555)
            )
        }

        if (enabled) {
            FloatingDraggableSnapDemo()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FloatingDraggableSnapDemo() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val density = LocalDensity.current
        val bubbleSize = 64.dp
        val bubblePx = with(density) { bubbleSize.toPx() }
        val maxX = constraints.maxWidth.toFloat() - bubblePx
        val maxY = constraints.maxHeight.toFloat() - bubblePx

        var offset by remember { mutableStateOf(Offset(24f, 200f)) }
        var isDragging by remember { mutableStateOf(false) }

        offset = Offset(
            offset.x.coerceIn(0f, maxX.coerceAtLeast(0f)),
            offset.y.coerceIn(0f, maxY.coerceAtLeast(0f))
        )

        val onDragEnd = rememberUpdatedState {
            val targetX = if (offset.x + bubblePx / 2f < constraints.maxWidth / 2f) {
                0f
            } else {
                maxX.coerceAtLeast(0f)
            }
            offset = Offset(targetX, offset.y.coerceIn(0f, maxY.coerceAtLeast(0f)))
        }

        Box(
            modifier = Modifier
                .size(bubbleSize)
                .align(Alignment.TopStart)
                .offset { IntOffset(offset.x.toInt(), offset.y.toInt()) }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isDragging = true },
                        onDragEnd = {
                            isDragging = false
                            onDragEnd.value()
                        },
                        onDragCancel = {
                            isDragging = false
                            onDragEnd.value()
                        }
                    ) { change, dragAmount ->
                        change.consume()
                        offset = Offset(
                            (offset.x + dragAmount.x).coerceIn(0f, maxX.coerceAtLeast(0f)),
                            (offset.y + dragAmount.y).coerceIn(0f, maxY.coerceAtLeast(0f))
                        )
                    }
                }
                .background(
                    color = if (isDragging) Color(0xFF3F51B5) else Color(0xFF607D8B),
                    shape = CircleShape
                )
        )
    }
}
