package prakhar.udemy.jetpackcompose.jetreader.screens.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
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
                    ShowBookDetails(bookInfo, navController)
                }

            }

        }

    }

}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {

    val googleBookId = bookInfo.data?.id
    val bookData = bookInfo.data?.volumeInfo


    Card(shape = RoundedCornerShape(10.dp), elevation = 4.dp) {
        Image(
            painter = rememberAsyncImagePainter(model = bookData!!.imageLinks.thumbnail),
            contentDescription = "Book Thumbnail",
            modifier = Modifier
                .width(130.dp)
                .height(150.dp)
                .padding(1.dp)
        )
    }


    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = bookData?.title.toString(),
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            maxLines = 19
        )
        Text(text = "Authors: ${bookData?.authors.toString()}")
        Text(text = "Page Count: ${bookData?.pageCount.toString()}")
        Text(
            text = "Categories: ${bookData?.categories.toString()}",
            style = MaterialTheme.typography.subtitle1,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Published: ${bookData?.publishedDate.toString()}",
            style = MaterialTheme.typography.subtitle1
        )
    }
    
}
