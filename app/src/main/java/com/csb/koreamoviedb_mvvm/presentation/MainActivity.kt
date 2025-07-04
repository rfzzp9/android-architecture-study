package com.csb.koreamoviedb_mvvm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csb.koreamoviedb_mvvm.presentation.detail.DetailScreen
import com.csb.koreamoviedb_mvvm.presentation.search.SearchScreen
import com.csb.koreamoviedb_mvvm.RootScreen
import com.csb.koreamoviedb_mvvm.presentation.theme.KoreaMovieDB_MVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoreaMovieDB_MVVMTheme {
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