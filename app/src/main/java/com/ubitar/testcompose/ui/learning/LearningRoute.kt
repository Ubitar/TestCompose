package com.ubitar.testcompose.ui.learning

sealed class LearningRoute(val route: String) {
    data object Home : LearningRoute("home")
    data object SafeDrawing : LearningRoute("safe_drawing")
    data object Navigation : LearningRoute("navigation_intro")
    data object NavigationDemoA : LearningRoute("navigation_demo_a")
    data object NavigationDemoB : LearningRoute("navigation_demo_b")
    data object NavigationDemoC : LearningRoute("navigation_demo_c")
    data object Modifier : LearningRoute("modifier_intro")
    data object Pager : LearningRoute("pager_intro")
    data object State : LearningRoute("state_intro")
    data object ViewModel : LearningRoute("viewmodel_intro")
    data object ViewModelPageScoped : LearningRoute("viewmodel_page_scoped")
    data object ViewModelSharedGraph : LearningRoute("viewmodel_shared_graph")
    data object ViewModelSharedGraphA : LearningRoute("viewmodel_shared_graph_a")
    data object ViewModelSharedGraphB : LearningRoute("viewmodel_shared_graph_b")
    data object ViewModelActivityScoped : LearningRoute("viewmodel_activity_scoped")
    data object Video : LearningRoute("video_intro")
}
