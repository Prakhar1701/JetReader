package prakhar.udemy.jetpackcompose.jetreader.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import prakhar.udemy.jetpackcompose.jetreader.components.FABContent
import prakhar.udemy.jetpackcompose.jetreader.components.ListCard
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.components.TitleSection
import prakhar.udemy.jetpackcompose.jetreader.model.MBook
import prakhar.udemy.jetpackcompose.jetreader.navigation.ReaderScreens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(title = "JetReader", navController = navController)
    },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            //Home content
            HomeContent(navController = navController, viewModel)
        }

    }
}


@Composable
fun HomeContent(
    navController: NavController,
    viewModel: HomeScreenViewModel
) {

//    val listOfBooks = listOf(
//        MBook(id = "101", title = "Hello", authors = "All of us", notes = null),
//        MBook(id = "102", title = "Hello Again", authors = "All of us", notes = null),
//        MBook(id = "103", title = "Hi!", authors = "The world us", notes = null),
//        MBook(id = "104", title = "I am Prakhar", authors = "All of us", notes = null),
//        MBook(id = "105", title = "Love You :)", authors = "All of us", notes = null)
//    )

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }

        Log.d("LIST OF BOOK FOR USER", "HomeContent: ${listOfBooks.toString()}")
    }


    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) {
        //prakhar@me.com -> prakhar
        FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    } else "N/A"
    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {

            TitleSection(label = "Your reading \n" + "activity right now...")

            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant)

                Text(
                    text = currentUserName!!, //!! -> Null Check
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }

        ReadingRightNowArea(listOfBooks = listOfBooks, navController = navController)

        TitleSection(label = "Reading List")

        // BookListArea(listOfBooks = emptyList(), navController = navController)
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {

    val addedBooks = listOfBooks.filter { mBook ->  //Books added to reading list.
        mBook.startedReading == null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(addedBooks) {
        Log.d("Reading List", "BookListArea $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}


@Composable
fun ReadingRightNowArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {
    //Filtering books by reading now
    val readingNowList =
        listOfBooks.filter { mBook ->
            mBook.startedReading != null && mBook.finishedReading == null
        }

    HorizontalScrollableComponent(listOfBooks = readingNowList) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (viewModel.data.value.loading == true) {

            CircularProgressIndicator(modifier = Modifier.size(80.dp))

        } else if (listOfBooks.isEmpty()) {

            Surface(modifier = Modifier.padding(23.dp)) {
                Text(
                    text = "No books found, add a book!", style = TextStyle(
                        color = Color.Red.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                )
            }

        } else {

            for (book in listOfBooks) {
                ListCard(book) {
                    onCardPressed(book.googleBookId.toString())
                }
            }

        }

    }
}