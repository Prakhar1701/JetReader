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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import prakhar.udemy.jetpackcompose.jetreader.components.FABContent
import prakhar.udemy.jetpackcompose.jetreader.components.ListCard
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.components.TitleSection
import prakhar.udemy.jetpackcompose.jetreader.model.MBook
import prakhar.udemy.jetpackcompose.jetreader.navigation.ReaderScreens

@Composable
fun HomeScreen(navController: NavController) {
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
            HomeContent(navController = navController)
        }

    }
}

@Preview
@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {
    val listOfBooks = listOf(
        MBook(id = "101", title = "Hello", authors = "All of us", notes = null),
        MBook(id = "102", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "103", title = "Hi!", authors = "The world us", notes = null),
        MBook(id = "104", title = "I am Prakhar", authors = "All of us", notes = null),
        MBook(id = "105", title = "Love You :)", authors = "All of us", notes = null)
    )
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

            TitleSection(label = "Your reading \n " + " activity right now...")

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

        ReadingRightNowArea(listOfBooks = listOf(), navController = navController)

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
    HorizontalScrollableComponent(listOfBooks) {
        Log.d("Reading List", "BookListArea $it")
        //TODO: On card clicked navigate to details
    }
}


@Composable
fun ReadingRightNowArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {
    ListCard()
}


@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}