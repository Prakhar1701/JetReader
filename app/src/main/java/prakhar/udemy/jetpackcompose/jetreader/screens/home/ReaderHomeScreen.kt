package prakhar.udemy.jetpackcompose.jetreader.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "JetReader", navController = navController)
    },
        floatingActionButton = {
            FABContent {}
        }) { contentPadding ->
        // Screen content
        Box(modifier = Modifier.padding(contentPadding)) { /* ... */ }
    }
}

@Composable
fun FABContent(onTop: () -> Unit) {
    FloatingActionButton(
        onClick = { onTop() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color(0XFF92CBDF)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White
        )
    }
}