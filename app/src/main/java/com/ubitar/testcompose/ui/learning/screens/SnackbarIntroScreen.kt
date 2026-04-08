package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SnackbarIntroScreen(onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color(0xFFF5EFE4),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BackPill(onBack = onBack)

            Text(
                text = "Snackbar 提示示例",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Snackbar 更适合 Compose 页面内的反馈，因为它可以放在 Scaffold 里统一管理，还能顺手加上操作按钮。",
                style = MaterialTheme.typography.bodyLarge
            )

            SectionCard(
                title = "推荐场景",
                body = "保存成功、删除完成、网络异常、支持撤销的操作反馈，都很适合用 Snackbar。它比 Toast 更贴近当前页面，也更容易跟状态流结合。"
            )

            CodeLikeLine("val snackbarHostState = remember { SnackbarHostState() }")
            CodeLikeLine("scope.launch { snackbarHostState.showSnackbar(\"保存成功\") }")

            Button(
                onClick = {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = "资料已保存",
                            actionLabel = "撤销",
                            duration = SnackbarDuration.Short
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            snackbarHostState.showSnackbar("已撤销刚才的操作")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text("显示 Snackbar")
            }
        }
    }
}
