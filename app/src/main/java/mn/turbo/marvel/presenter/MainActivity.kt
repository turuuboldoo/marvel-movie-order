package mn.turbo.marvel.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mn.turbo.marvel.presenter.movie.detail.MovieDetailScreen
import mn.turbo.marvel.presenter.movie.list.component.MovieListScreen
import mn.turbo.marvel.presenter.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(darkTheme = true) {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MovieListScreen.route
                    ) {
                        composable(
                            route = Screen.MovieListScreen.route
                        ) {
                            MovieListScreen(navController)
                        }
                        composable(
                            route = Screen.MovieDetailScreen.route + "/{movieId}"
                        ) {
                            MovieDetailScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
