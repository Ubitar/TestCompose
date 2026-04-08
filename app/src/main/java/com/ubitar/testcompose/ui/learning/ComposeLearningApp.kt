package com.ubitar.testcompose.ui.learning

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubitar.testcompose.ui.learning.screens.HomeScreen
import com.ubitar.testcompose.ui.learning.screens.ModifierIntroScreen
import com.ubitar.testcompose.ui.learning.screens.NavigationDemoScreen
import com.ubitar.testcompose.ui.learning.screens.NavigationIntroScreen
import com.ubitar.testcompose.ui.learning.screens.SafeDrawingPaddingScreen
import com.ubitar.testcompose.ui.learning.screens.StateIntroScreen

@Composable
fun ComposeLearningApp() {
    val navController = rememberNavController()
    val navigator = remember(navController) { AppNavigator(navController) }

    NavHost(
        navController = navController,
        startDestination = LearningRoute.Home.route
    ) {
        composable(LearningRoute.Home.route) {
            HomeScreen(
                onOpenSafeDrawing = navigator::openSafeDrawing,
                onOpenNavigation = navigator::openNavigationIntro,
                onOpenModifier = navigator::openModifierIntro,
                onOpenState = navigator::openStateIntro
            )
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
        composable(LearningRoute.State.route) {
            StateIntroScreen(onBack = navigator::back)
        }
    }
}
