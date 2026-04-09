package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogIntroScreen(onBack: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LearningScreen(
        title = "弹窗示例合集",
        summary = "集中演示 Compose 的弹窗写法，后续可以继续追加更多样式与交互。",
        onBack = onBack
    ) {
        SectionCard(
            title = "关键点",
            body = "Dialog 默认居中展示；内容区域由你自己定义。通常用 Card 搭配遮罩，就能得到清晰的弹窗层级。"
        )
        SectionCard(
            title = "适用场景",
            body = "需要用户立即确认或输入时使用弹窗，比如删除确认、选择操作、补充信息。"
        )

        CodeLikeLine("Dialog(onDismissRequest = { showDialog = false }) { /* 弹窗内容 */ }")

        ActionButton(
            text = "打开居中弹窗",
            onClick = { showDialog = true },
            color = Color(0xFF5C3D8C)
        )

        ActionButton(
            text = "打开下方弹窗",
            onClick = { showBottomSheet = true },
            color = Color(0xFF26547C)
        )

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "删除提醒",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "该操作无法撤销，确认要继续吗？",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                            ) {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("取消")
                                }
                                Button(onClick = { showDialog = false }) {
                                    Text("确认")
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showBottomSheet) {
            val configuration = LocalConfiguration.current
            val maxSheetHeight = (configuration.screenHeightDp * 0.6f).dp
            val sheetScrollState = rememberScrollState()

            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = bottomSheetState,
                containerColor = Color(0xFFF7F2E8)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                        .heightIn(max = maxSheetHeight)
                        .verticalScroll(sheetScrollState),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "下方弹窗",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "这是一个从底部滑出的弹窗，默认占满屏幕宽度。最大高度按屏幕高度的 60% 计算。",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    SectionCard(
                        title = "常见用途",
                        body = "更适合承载一组操作或信息列表，比如筛选、选择项、确认流程。"
                    )
                    repeat(6) { index ->
                        SectionCard(
                            title = "示例内容 ${index + 1}",
                            body = "这里可以放更多说明文字，用来触发滚动效果。"
                        )
                    }
                    ActionButton(
                        text = "关闭",
                        onClick = { showBottomSheet = false },
                        color = Color(0xFF203A43)
                    )
                }
            }
        }
    }
}
