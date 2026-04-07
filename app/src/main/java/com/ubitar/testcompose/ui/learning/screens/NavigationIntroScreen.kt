package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.runtime.Composable

@Composable
fun NavigationIntroScreen(onBack: () -> Unit) {
    LearningScreen(
        title = "Compose 页面跳转机制",
        summary = "Compose 里常见的页面切换，不是 inflate XML 然后 findViewById，也不是把 Fragment 塞进容器里，而是通过状态驱动的 NavHost 来决定“当前该显示哪个 Composable”。",
        onBack = onBack
    ) {
        section(
            title = "这个项目现在怎么跳转",
            body = "MainActivity 只负责 setContent。ComposeLearningApp 里创建 NavController，再由 NavHost 按 route 渲染不同页面。点击首页卡片时，调用 navController.navigate(route)；返回时用 popBackStack()。"
        )
        block {
            CodeLikeLine("val navController = rememberNavController()")
            CodeLikeLine("NavHost(navController, startDestination = \"home\") { ... }")
            CodeLikeLine("navController.navigate(\"safe_drawing\")")
        }
        section(
            title = "和 XML / Fragment 的直觉差异",
            body = "传统 View 方案更像“把某个界面对象切进去”；Compose 更像“当前 route 变了，所以应该重新声明要显示哪棵 UI 树”。你操作的是状态和目标页面，而不是手动管理一堆 View 引用。"
        )
        section(
            title = "你可以重点观察的文件",
            body = "MainActivity.kt 是入口；ComposeLearningApp.kt 是导航总控；LearningRoute.kt 管路由；各个 Screen 文件就是一个个页面函数。这个拆法比 XML 页面 + Fragment 类更扁平。"
        )
    }
}
