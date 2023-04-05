package prakhar.udemy.jetpackcompose.jetreader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import prakhar.udemy.jetpackcompose.jetreader.screens.ReaderSplashScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.details.BookDetailsScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.home.HomeScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.login.ReaderLoginScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.search.BooksSearchViewModel
import prakhar.udemy.jetpackcompose.jetreader.screens.search.SearchScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.stats.ReaderStatsScreen
import prakhar.udemy.jetpackcompose.jetreader.screens.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            HomeScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<BooksSearchViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }

        val detailName = ReaderScreens.DetailScreen.name
        composable(
            "$detailName/{bookId}", arguments = listOf(navArgument("bookId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let { Id ->
                BookDetailsScreen(navController = navController, bookId = Id.toString())
            }
        }

        composable(ReaderScreens.UpdateScreen.name) {
            BookUpdateScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name) {
            ReaderStatsScreen(navController = navController)
        }
        composable(ReaderScreens.CreateAccountScreen.name) {
        }
    }
}