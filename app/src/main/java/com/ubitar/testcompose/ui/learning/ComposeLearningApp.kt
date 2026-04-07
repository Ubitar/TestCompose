package com.ubitar.testcompose.ui.learning

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ubitar.testcompose.ui.learning.screens.HomeScreen
import com.ubitar.testcompose.ui.learning.screens.ModifierIntroScreen
import com.ubitar.testcompose.ui.learning.screens.NavigationIntroScreen
import com.ubitar.testcompose.ui.learning.screens.SafeDrawingPaddingScreen
import com.ubitar.testcompose.ui.learning.screens.StateIntroScreen

@Preview
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
            NavigationIntroScreen(onBack = { navController.popBackStack() })
        }
        composable(LearningRoute.Modifier.route) {
            ModifierIntroScreen(onBack = { navController.popBackStack() })
        }
        composable(LearningRoute.State.route) {
            StateIntroScreen(onBack = { navController.popBackStack() })
        }
    }
}
