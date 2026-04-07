package com.ubitar.testcompose.ui.learning.screens
 
import androidx.compose.runtime.Composable

@Composable
fun StateIntroScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "状态与重组",
        summary = "Compose 的核心思想之一是：界面由状态决定。状态变了，相关 Composable 会重组，然后界面自动刷新。",
        onBack = onBack
    ) {
        section(
            title = "最常见的入口",
            body = "remember 用来在重组之间记住值，mutableStateOf 用来创建可观察状态。比如计数器点击一次，count 变了，显示 count 的 Text 就会自动更新。"
        )
        block {
            CodeLikeLine("var count by remember { mutableStateOf(0) }")
            CodeLikeLine("Button(onClick = { count++ }) { Text(\"${'$'}count\") }")
        }
        section(
            title = "和 View 时代的差别",
            body = "以前你经常手动拿到 TextView 再 setText；Compose 更倾向于改数据本身。数据一变，UI 重新声明。你维护的是状态，不是找控件。"
        )
        section(
            title = "为什么这很重要",
            body = "一旦你接受“UI 是状态函数”这个思路，Compose 里列表刷新、页面切换、表单输入都会顺很多，因为很多同步逻辑都能自然消失。"
        )
    }
}
