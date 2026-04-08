package com.ubitar.testcompose.ui.learning

import androidx.compose.runtime.Composable
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

    NavHost(
        navController = navController,
        startDestination = LearningRoute.Home.route
    ) {
        composable(LearningRoute.Home.route) {
            HomeScreen(
                onOpenSafeDrawing = { navController.navigate(LearningRoute.SafeDrawing.route) },
                onOpenNavigation = { navController.navigate(LearningRoute.Navigation.route) },
                onOpenModifier = { navController.navigate(LearningRoute.Modifier.route) },
                onOpenState = { navController.navigate(LearningRoute.State.route) }
            )
        }
        composable(LearningRoute.SafeDrawing.route) {
            SafeDrawingPaddingScreen(onBack = { navController.popBackStack() })
        }
        composable(LearningRoute.Navigation.route) {
            NavigationIntroScreen(
                onBack = { navController.popBackStack() },
                onOpenDemo = { navController.navigate(LearningRoute.NavigationDemoA.route) }
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
            ModifierIntroScreen(onBack = { navController.popBackStack() })
        }
        composable(LearningRoute.State.route) {
            StateIntroScreen(onBack = { navController.popBackStack() })
        }
    }
}
