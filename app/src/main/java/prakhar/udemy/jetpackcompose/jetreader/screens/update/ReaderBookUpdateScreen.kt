package prakhar.udemy.jetpackcompose.jetreader.screens.update

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BookUpdateScreen(
    navController: NavController,
    bookItemId: String,
) {
    Log.d("BOOK ITEM ID", "BookUpdateScreen: $bookItemId") //This is showing Book Title
}