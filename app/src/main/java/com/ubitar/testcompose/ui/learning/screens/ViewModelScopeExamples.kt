package com.ubitar.testcompose.ui.learning.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CounterUiState(
    val count: Int = 0,
    val message: String = "点击按钮，观察当前作用域下的状态是否会被复用。"
)

private fun buildMessage(scopeName: String, nextCount: Int): String {
    return "当前是 $scopeName，count = $nextCount。只要多个页面共用同一个 owner，它们看到的就会是同一个值。"
}

open class BaseCounterViewModel(
    private val scopeName: String
) : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun increase() {
        _uiState.update { state ->
            val nextCount = state.count + 1
            state.copy(
                count = nextCount,
                message = buildMessage(scopeName, nextCount)
            )
        }
    }

    fun reset() {
        _uiState.value = CounterUiState(
            message = "已经重置。现在这份状态仍然属于 $scopeName。"
        )
    }
}

class PageScopedCounterViewModel : BaseCounterViewModel("页面级 ViewModel")

class SharedGraphCounterViewModel : BaseCounterViewModel("NavGraph 级共享 ViewModel")

class ActivityLevelCounterViewModel : BaseCounterViewModel("Activity 级 ViewModel")

@Composable
fun PageScopedViewModelScreen(
    onBack: () -> Unit,
    pageScopedCounterViewModel: PageScopedCounterViewModel = viewModel()
) {
    val uiState by pageScopedCounterViewModel.uiState.collectAsStateWithLifecycle()

    CounterExampleScreen(
        title = "页面级 ViewModel",
        summary = "这里直接在当前页面里调用 viewModel()。默认 owner 是当前路由对应的 NavBackStackEntry，所以这份 ViewModel 主要服务于这个页面本身。",
        ownerDescription = "owner = 当前页面 route 对应的 NavBackStackEntry",
        uiState = uiState,
        onBack = onBack,
        onIncrease = pageScopedCounterViewModel::increase,
        onReset = pageScopedCounterViewModel::reset
    )
}

@Composable
fun SharedNavGraphViewModelScreen(
    title: String,
    onBack: () -> Unit,
    onNavigateNext: () -> Unit,
    navControllerRoute: String,
    navController: NavController
) {
    val parentEntry = navController.getBackStackEntry(navControllerRoute)
    val sharedGraphCounterViewModel: SharedGraphCounterViewModel = viewModel(parentEntry)
    val uiState by sharedGraphCounterViewModel.uiState.collectAsStateWithLifecycle()

    CounterExampleScreen(
        title = title,
        summary = "页面 A 和页面 B 都从父 NavGraph 的 BackStackEntry 取 ViewModel，所以 count 会互相同步。你在 A 点 +1，去 B 还能看到同样的结果。",
        ownerDescription = "owner = 父 NavGraph(viewmodel_shared_graph) 的 NavBackStackEntry",
        uiState = uiState,
        onBack = onBack,
        onIncrease = sharedGraphCounterViewModel::increase,
        onReset = sharedGraphCounterViewModel::reset,
        extraActionText = "切换到另一个共享页面",
        onExtraAction = onNavigateNext
    )
}

@Composable
fun ActivityScopedViewModelScreen(
    onBack: () -> Unit,
    activityLevelCounterViewModel: ActivityLevelCounterViewModel
) {
    val uiState by activityLevelCounterViewModel.uiState.collectAsStateWithLifecycle()

    CounterExampleScreen(
        title = "Activity 级 ViewModel",
        summary = "这份 ViewModel 由 MainActivity 创建并持有，再传进 ComposeLearningApp。只要 MainActivity 还在，这份状态就可以被多个页面继续复用。",
        ownerDescription = "owner = MainActivity",
        uiState = uiState,
        onBack = onBack,
        onIncrease = activityLevelCounterViewModel::increase,
        onReset = activityLevelCounterViewModel::reset
    )
}

@Composable
private fun CounterExampleScreen(
    title: String,
    summary: String,
    ownerDescription: String,
    uiState: CounterUiState,
    onBack: () -> Unit,
    onIncrease: () -> Unit,
    onReset: () -> Unit,
    extraActionText: String? = null,
    onExtraAction: (() -> Unit)? = null
) {
    LearningScreen(
        title = title,
        summary = summary,
        onBack = onBack
    ) {
        section(
            title = "当前绑定的 owner",
            body = ownerDescription
        )
        block {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TinyTag(text = "当前 count = ${uiState.count}", color = Color(0xFF3A7D44))
                SectionCard(title = "状态说明", body = uiState.message)
                CodeLikeLine("val vm = viewModel<...>(owner)")
                CodeLikeLine("val uiState by vm.uiState.collectAsStateWithLifecycle()")
                ActionButton(
                    text = "让 ViewModel +1",
                    onClick = onIncrease,
                    color = Color(0xFF3A7D44)
                )
                ActionButton(
                    text = "重置状态",
                    onClick = onReset,
                    color = Color(0xFF8D6E63)
                )
                if (extraActionText != null && onExtraAction != null) {
                    ActionButton(
                        text = extraActionText,
                        onClick = onExtraAction,
                        color = Color(0xFFD94F3D)
                    )
                }
            }
        }
    }
}
