package prakhar.udemy.jetpackcompose.jetreader.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar

@Preview
@Composable
fun SearchScreen(
    navController: NavController = NavController(LocalContext.current),
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.popBackStack()  //Works Better :)
//          navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) { /* ... */ }
    }
}