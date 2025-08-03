package com.csb.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csb.presentation.detail.screen.DetailScreen
import com.csb.presentation.search.screen.SearchScreen
import com.csb.presentation.ui.theme.KoreaMovieDB_CleanArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoreaMovieDB_CleanArchitectureTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    RootNavScreen()
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun RootNavScreen(

) {
    val rootScreenNavController = rememberNavController()


    NavHost(
        navController = rootScreenNavController,
        startDestination = RootScreen.SCREEN_MAIN_SCREEN.name,
        enterTransition = {
            fadeIn(tween(100))
        },
        popExitTransition = {
            fadeOut(tween(100))
        },
        exitTransition = {
            fadeOut(tween(100))
        },
        popEnterTransition = {
            fadeIn(tween(100))
        }
    ) {
        composable(route = RootScreen.SCREEN_MAIN_SCREEN.name) {
            SearchScreen(rootScreenNavController)
        }

        composable(route = RootScreen.SCREEN_DETAIL.name) {
            DetailScreen(rootScreenNavController)
        }
    }
}