package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ViewModelIntroScreen(
    onBack: () -> Unit,
    onOpenPageScoped: () -> Unit,
    onOpenSharedGraph: () -> Unit,
    onOpenActivityScoped: () -> Unit
) {
    LearningScreen(
        title = "Compose 和 ViewModel 作用域",
        summary = "同样都是 viewModel，真正决定是不是同一个实例的关键，不是 Composable 名字，而是它绑定到哪个 ViewModelStoreOwner。下面这 3 个例子分别演示页面级、NavGraph 级共享、以及 Activity 级共享。",
        onBack = onBack
    ) {
        section(
            title = "一句话结论",
            body = "同一个 ViewModelStoreOwner 会拿到同一个 ViewModel 实例；换了 owner，就会得到另一份实例。Compose 页面只是使用者，本身不是 ViewModel 的生命周期宿主。"
        )
        section(
            title = "作用域关系图",
            body = "先看这张图，再点下面的入口去观察 count 的共享行为。"
        )
        block {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                CodeLikeLine(
                    """
                    MainActivity (Activity owner)
                    └─ ComposeLearningApp
                       └─ NavHost
                          ├─ Route: viewmodel_page_scoped
                          │  └─ owner = 当前页面的 NavBackStackEntry
                          │     └─ PageScopedCounterViewModel
                          ├─ NavGraph: viewmodel_shared_graph
                          │  ├─ Route A
                          │  └─ Route B
                          │     └─ 共同 owner = 父 NavGraph 的 NavBackStackEntry
                          │        └─ SharedGraphCounterViewModel
                          └─ Route: viewmodel_activity_scoped
                             └─ owner = MainActivity
                                └─ ActivityLevelCounterViewModel
                    """.trimIndent()
                )
                TinyTag(text = "页面级", color = Color(0xFF2B6F63))
                TinyTag(text = "NavGraph 级共享", color = Color(0xFFD94F3D))
                TinyTag(text = "Activity 级", color = Color(0xFF26547C))
            }
        }
        block {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                EntryCard(
                    title = "页面级 ViewModel",
                    description = "只绑定当前路由页面。默认最常见，也最容易先理解。",
                    color = Color(0xFF2B6F63),
                    onClick = onOpenPageScoped
                )
                EntryCard(
                    title = "多个页面共享同一个 NavGraph 级 ViewModel",
                    description = "页面 A 和页面 B 通过同一个父 NavGraph owner 获取实例，所以 count 会同步。",
                    color = Color(0xFFD94F3D),
                    onClick = onOpenSharedGraph
                )
                EntryCard(
                    title = "Activity 级 ViewModel",
                    description = "由 MainActivity 持有，只要 Activity 活着，这份状态就能被多个页面拿来复用。",
                    color = Color(0xFF26547C),
                    onClick = onOpenActivityScoped
                )
            }
        }
    }
}
