package com.ubitar.testcompose

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ubitar.testcompose.ui.learning.ComposeLearningApp
import com.ubitar.testcompose.ui.theme.TestComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        val launchTime = SystemClock.elapsedRealtime()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            SystemClock.elapsedRealtime() - launchTime < 1_000L
        }

        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComposeLearningApp()
                }
            }
        }
    }
}
