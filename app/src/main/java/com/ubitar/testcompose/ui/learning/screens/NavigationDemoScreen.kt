package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ubitar.testcompose.ui.learning.LearningRoute

@Composable
fun NavigationDemoScreen(
    pageName: String,
    currentRoute: String,
    navController: NavController
) {
    val stackText = navController.currentBackStack.value
        .mapNotNull { it.destination.route }
        .joinToString(" -> ")

    LearningScreen(
        title = "三页面导航实验: $pageName",
        summary = "这里有 A、B、C 三个页面。你可以反复点按钮，观察返回路径和栈的变化，从而体会普通跳转、launchSingleTop 和 popUpTo(inclusive = ...) 的差异。",
        onBack = { navController.popBackStack() }
    ) {
        section(
            title = "当前导航栈",
            body = stackText.ifBlank { "当前还没有可读的 route 信息" }
        )
        section(
            title = "普通 navigate",
            body = "每点一次都会继续压栈。哪怕目标页和当前页一样，也会再进一层，所以很容易出现 A -> B -> B 这种重复页面。"
        )
        block {
            PageJumpButtons(currentRoute, navController, DemoMode.Normal)
        }
        section(
            title = "launchSingleTop = true",
            body = "如果目标页面已经在栈顶，就不要再新增一个实例。它只对栈顶相同这一件事生效，不会帮你清掉更早位置的同名页面。"
        )
        block {
            PageJumpButtons(currentRoute, navController, DemoMode.SingleTop)
        }
        section(
            title = "popUpTo + inclusive",
            body = "先把栈弹到某个指定 route，再决定是否把那个目标 route 自己也一起删掉。inclusive = false 表示保留目标页；inclusive = true 表示连目标页一起删除。"
        )
        block {
            ActionButton(
                text = "回到 A，保留 A: popUpTo(A) + inclusive=false + launchSingleTop",
                color = Color(0xFF9C6644),
                onClick = {
                    navController.navigate(LearningRoute.NavigationDemoA.route) {
                        popUpTo(LearningRoute.NavigationDemoA.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
            ActionButton(
                text = "回到 A，但先删掉 A: popUpTo(A) + inclusive=true",
                color = Color(0xFFD94F3D),
                onClick = {
                    navController.navigate(LearningRoute.NavigationDemoA.route) {
                        popUpTo(LearningRoute.NavigationDemoA.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        section(
            title = "inclusive 到底干了什么",
            body = "如果当前栈是 home -> A -> B -> C，popUpTo(A) 时，inclusive=false 会删掉 B 和 C，保留 A；inclusive=true 会把 A、B、C 全删掉。然后这次 navigate 再决定要不要放入一个新的 A。"
        )
        section(
            title = "launchSingleTop 的书写顺序",
            body = "在同一个 navigate { ... } 配置块里，先写 launchSingleTop 再写 popUpTo，或者反过来，通常不会影响结果。因为它们是在给这次导航收集参数，不是按代码顺序立刻执行。真正重要的是有没有同时配置，以及最终目标 route 是什么。"
        )
    }
}

private enum class DemoMode {
    Normal,
    SingleTop
}

@Composable
private fun PageJumpButtons(
    currentRoute: String,
    navController: NavController,
    mode: DemoMode
) {
    val labelSuffix = if (mode == DemoMode.SingleTop) " + launchSingleTop" else ""
    val color = if (mode == DemoMode.SingleTop) Color(0xFF2B6F63) else Color(0xFF26547C)

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        DemoJumpButton("去页面 A$labelSuffix", LearningRoute.NavigationDemoA.route, currentRoute, navController, mode, color)
        DemoJumpButton("去页面 B$labelSuffix", LearningRoute.NavigationDemoB.route, currentRoute, navController, mode, color)
        DemoJumpButton("去页面 C$labelSuffix", LearningRoute.NavigationDemoC.route, currentRoute, navController, mode, color)
    }
}

@Composable
private fun DemoJumpButton(
    text: String,
    route: String,
    currentRoute: String,
    navController: NavController,
    mode: DemoMode,
    color: Color
) {
    val finalText = if (route == currentRoute) "$text（当前页也可点）" else text
    ActionButton(
        text = finalText,
        color = color,
        onClick = {
            navController.navigate(route) {
                if (mode == DemoMode.SingleTop) {
                    launchSingleTop = true
                }
            }
        }
    )
}
