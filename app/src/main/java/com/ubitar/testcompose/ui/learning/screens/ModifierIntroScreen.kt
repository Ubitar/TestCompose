package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.runtime.Composable

@Composable
fun ModifierIntroScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "Modifier 是怎么工作的",
        summary = "Compose 里很多布局、绘制和交互能力都挂在 Modifier 链上。你可以把它理解成“给 Composable 一层一层追加行为”。",
        onBack = onBack
    ) {
        section(
            title = "最常见的感觉",
            body = "fillMaxWidth() 决定占多宽，background() 决定画什么底色，padding() 决定内容缩进去多少，clickable() 决定能不能点。它们不是 XML 属性，而是按顺序串起来的行为。"
        )
        block {
            CodeLikeLine("Modifier.fillMaxWidth().background(Color.Red).padding(16.dp)")
        }
        section(
            title = "顺序为什么重要",
            body = "因为 Modifier 是链。先 background 再 padding，和先 padding 再 background，最后看到的色块范围会不一样。Compose 很强调“声明顺序会影响结果”。"
        )
        section(
            title = "和 XML 的差别",
            body = "XML 里你常写 layout_width、padding、background 这些离散属性；Compose 则把这些统一成 Kotlin API，能组合、复用、提取变量，也更容易写条件逻辑。"
        )
    }
}
