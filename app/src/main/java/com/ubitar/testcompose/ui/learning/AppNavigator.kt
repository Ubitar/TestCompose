package com.ubitar.testcompose.ui.learning

import androidx.navigation.NavHostController

class AppNavigator(
    private val navController: NavHostController
) {
    fun back() {
        navController.popBackStack()
    }

    fun openHome() = openSingleTop(LearningRoute.Home.route)

    fun openSnackbarIntro() = openSingleTop(LearningRoute.Snackbar.route)

    fun openSafeDrawing() = openSingleTop(LearningRoute.SafeDrawing.route)

    fun openNavigationIntro() = openSingleTop(LearningRoute.Navigation.route)

    fun openModifierIntro() = openSingleTop(LearningRoute.Modifier.route)

    fun openCenterDialogIntro() = openSingleTop(LearningRoute.CenterDialog.route)

    fun openPagerIntro() = openSingleTop(LearningRoute.Pager.route)

    fun openStateIntro() = openSingleTop(LearningRoute.State.route)

    fun openViewModelIntro() = openSingleTop(LearningRoute.ViewModel.route)

    fun openPageScopedViewModel() = openSingleTop(LearningRoute.ViewModelPageScoped.route)

    fun openSharedGraphViewModel() = openSingleTop(LearningRoute.ViewModelSharedGraph.route)

    fun openActivityScopedViewModel() = openSingleTop(LearningRoute.ViewModelActivityScoped.route)

    fun openVideoIntro() = openSingleTop(LearningRoute.Video.route)

    fun openNavigationDemo(route: String) = openSingleTop(route)

    private fun openSingleTop(route: String) {
        if (navController.currentDestination?.route == route) return
        navController.navigate(route) {
            launchSingleTop = true
        }
    }
}
