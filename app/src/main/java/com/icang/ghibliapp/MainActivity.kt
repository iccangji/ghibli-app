package com.icang.ghibliapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room.databaseBuilder
import com.icang.ghibliapp.data.local.AppDatabase
import com.icang.ghibliapp.data.repository.FilmRepository
import com.icang.ghibliapp.ui.screen.DetailScreen
import com.icang.ghibliapp.ui.screen.ListScreen
import com.icang.ghibliapp.viewmodel.FilmViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "films-db"
        ).build()

        val repository = FilmRepository(db.filmDao())
        val viewModel = FilmViewModel(repository)

        setContent {
            MaterialTheme {
                AppNavHost(viewModel)
            }
        }

        viewModel.loadFilms()
    }
}


@Composable
fun AppNavHost(viewModel: FilmViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable("list") {
            ListScreen(viewModel) { film ->
                navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("url", film.url)
                navController.navigate("detail")
            }
        }
        composable("detail") {
            val filmUrl = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("url") ?: ""

            DetailScreen(viewModel = viewModel, url = filmUrl, navController)
        }
    }

}