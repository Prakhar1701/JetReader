package prakhar.udemy.jetpackcompose.jetreader.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar

@Composable
fun BookDetailsScreen(navController: NavController, bookId: String) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack();
//            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .padding(3.dp)
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Book Id: $bookId")
            }

        }

    }

}