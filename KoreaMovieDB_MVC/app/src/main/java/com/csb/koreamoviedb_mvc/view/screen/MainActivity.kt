package com.csb.koreamoviedb_mvc.view.screen

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.csb.koreamoviedb_mvc.controller.MovieController
import com.csb.koreamoviedb_mvc.model.repository.MovieRepository
import com.csb.koreamoviedb_mvc.tools.RootScreen
import com.csb.koreamoviedb_mvc.view.theme.KoreaMovieDB_MVCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoreaMovieDB_MVCTheme {
                RootNavScreen(this)
            }
        }
    }
}

@Composable
fun RootNavScreen(
    context: Context
){
    val rootScreenNavController = rememberNavController()

    val movieController = MovieController(context, MovieRepository(context))

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
    ){
        composable(route = RootScreen.SCREEN_MAIN_SCREEN.name) {
            SearchScreen(rootScreenNavController,context,movieController)
        }

        composable(route = RootScreen.SCREEN_DETAIL.name) {
            DetailScreen(rootScreenNavController,context)
        }
    }
}

