package prakhar.udemy.jetpackcompose.jetreader.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import prakhar.udemy.jetpackcompose.jetreader.components.InputField
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.model.MBook

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
        Surface(modifier = Modifier.padding(contentPadding)) {
            Column {

                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Log.d("SearchForm", "SearchScreen: $it")
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController) {

    val listOfBooks = listOf(
        MBook(id = "101", title = "Hello", authors = "All of us", notes = null),
        MBook(id = "102", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "103", title = "Hi!", authors = "The world us", notes = null),
        MBook(id = "104", title = "I am Prakhar", authors = "All of us", notes = null),
        MBook(id = "105", title = "Love You :)", authors = "All of us", notes = null)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = listOfBooks) { book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(
    book: MBook,
    navController: NavController
) {
    Card(modifier = Modifier
        .clickable {}
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp), shape = RectangleShape,
        elevation = 7.dp) {

        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl =
                "https://books.google.co.in/books/publisher/content?id=yng_CwAAQBAJ&pg=PP1&img=1&zoom=3&hl=en&bul=1&sig=ACfU3U1kgxrlkVTslOGgCqkLy6v9ANZV8g&w=1280"

            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
            )

            Column {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)

                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}
) {
    Column {

        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value) {
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )

    }
}