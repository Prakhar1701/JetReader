package prakhar.udemy.jetpackcompose.jetreader.screens.details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.data.Resource
import prakhar.udemy.jetpackcompose.jetreader.model.Item

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {

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
                //    val bookInfo = viewModel.getBookInfo(bookId = bookId) //Error... //Use ProductionState :)
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                Log.d("DETAILS", "BookDetailsScreen: ${bookInfo.data.toString()}")

                if (bookInfo.data == null) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Book Title: ${bookInfo.data.volumeInfo.title}")
                }

            }

        }

    }

}