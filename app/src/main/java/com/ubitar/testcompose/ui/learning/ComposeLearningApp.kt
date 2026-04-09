package com.ubitar.testcompose.ui.learning

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ubitar.testcompose.ui.learning.screens.ActivityLevelCounterViewModel
import com.ubitar.testcompose.ui.learning.screens.ActivityScopedViewModelScreen
import com.ubitar.testcompose.ui.learning.screens.DialogIntroScreen
import com.ubitar.testcompose.ui.learning.screens.HomeScreen
import com.ubitar.testcompose.ui.learning.screens.ModifierIntroScreen
import com.ubitar.testcompose.ui.learning.screens.NavigationDemoScreen
import com.ubitar.testcompose.ui.learning.screens.NavigationIntroScreen
import com.ubitar.testcompose.ui.learning.screens.PageScopedViewModelScreen
import com.ubitar.testcompose.ui.learning.screens.PagerIntroScreen
import com.ubitar.testcompose.ui.learning.screens.SafeDrawingPaddingScreen
import com.ubitar.testcompose.ui.learning.screens.SnackbarIntroScreen
import com.ubitar.testcompose.ui.learning.screens.SharedNavGraphViewModelScreen
import com.ubitar.testcompose.ui.learning.screens.StateIntroScreen
import com.ubitar.testcompose.ui.learning.screens.ViewModelIntroScreen
import com.ubitar.testcompose.ui.learning.screens.VideoPlayerAdvancedScreen

@Composable
fun ComposeLearningApp(
    activityLevelCounterViewModel: ActivityLevelCounterViewModel
) {
    val navController = rememberNavController()
    val navigator = remember(navController) { AppNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = LearningRoute.Home.route
    ) {
        composable(LearningRoute.Home.route) {
            HomeScreen(
                onOpenSnackbar = navigator::openSnackbarIntro,
                onOpenSafeDrawing = navigator::openSafeDrawing,
                onOpenNavigation = navigator::openNavigationIntro,
                onOpenModifier = navigator::openModifierIntro,
                onOpenCenterDialog = navigator::openCenterDialogIntro,
                onOpenPager = navigator::openPagerIntro,
                onOpenState = navigator::openStateIntro,
                onOpenViewModel = navigator::openViewModelIntro,
                onOpenVideo = navigator::openVideoIntro
            )
        }
        composable(LearningRoute.Snackbar.route) {
            SnackbarIntroScreen(onBack = navigator::back)
        }
        composable(LearningRoute.SafeDrawing.route) {
            SafeDrawingPaddingScreen(onBack = navigator::back)
        }
        composable(LearningRoute.Navigation.route) {
            NavigationIntroScreen(
                onBack = navigator::back,
                onOpenDemo = { navigator.openNavigationDemo(LearningRoute.NavigationDemoA.route) }
            )
        }
        composable(LearningRoute.NavigationDemoA.route) {
            NavigationDemoScreen("页面 A", LearningRoute.NavigationDemoA.route, navController)
        }
        composable(LearningRoute.NavigationDemoB.route) {
            NavigationDemoScreen("页面 B", LearningRoute.NavigationDemoB.route, navController)
        }
        composable(LearningRoute.NavigationDemoC.route) {
            NavigationDemoScreen("页面 C", LearningRoute.NavigationDemoC.route, navController)
        }
        composable(LearningRoute.Modifier.route) {
            ModifierIntroScreen(onBack = navigator::back)
        }
        composable(LearningRoute.CenterDialog.route) {
            DialogIntroScreen(onBack = navigator::back)
        }
        composable(LearningRoute.Pager.route) {
            PagerIntroScreen(onBack = navigator::back)
        }
        composable(LearningRoute.State.route) {
            StateIntroScreen(onBack = navigator::back)
        }
        composable(LearningRoute.ViewModel.route) {
            ViewModelIntroScreen(
                onBack = navigator::back,
                onOpenPageScoped = navigator::openPageScopedViewModel,
                onOpenSharedGraph = navigator::openSharedGraphViewModel,
                onOpenActivityScoped = navigator::openActivityScopedViewModel
            )
        }
        composable(LearningRoute.ViewModelPageScoped.route) {
            PageScopedViewModelScreen(onBack = navigator::back)
        }
        navigation(
            startDestination = LearningRoute.ViewModelSharedGraphA.route,
            route = LearningRoute.ViewModelSharedGraph.route
        ) {
            composable(LearningRoute.ViewModelSharedGraphA.route) {
                SharedNavGraphViewModelScreen(
                    title = "NavGraph 级共享 ViewModel - 页面 A",
                    onBack = navigator::back,
                    onNavigateNext = {
                        navigator.openNavigationDemo(LearningRoute.ViewModelSharedGraphB.route)
                    },
                    navControllerRoute = LearningRoute.ViewModelSharedGraph.route,
                    navController = navController
                )
            }
            composable(LearningRoute.ViewModelSharedGraphB.route) {
                SharedNavGraphViewModelScreen(
                    title = "NavGraph 级共享 ViewModel - 页面 B",
                    onBack = navigator::back,
                    onNavigateNext = {
                        navigator.openNavigationDemo(LearningRoute.ViewModelSharedGraphA.route)
                    },
                    navControllerRoute = LearningRoute.ViewModelSharedGraph.route,
                    navController = navController
                )
            }
        }
        composable(LearningRoute.ViewModelActivityScoped.route) {
            ActivityScopedViewModelScreen(
                onBack = navigator::back,
                activityLevelCounterViewModel = activityLevelCounterViewModel
            )
        }
        composable(LearningRoute.Video.route) {
            VideoPlayerAdvancedScreen(onBack = navigator::back)
        }
    }
}
