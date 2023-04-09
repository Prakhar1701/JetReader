package prakhar.udemy.jetpackcompose.jetreader.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.model.MBook
import prakhar.udemy.jetpackcompose.jetreader.screens.home.HomeScreenViewModel
import java.util.*

@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {

            ReaderAppBar(
                title = "Book Stats",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController
            ) {
                navController.popBackStack()
            }

        },
    ) {
        Surface(modifier = Modifier.padding(it)) {

            //only show books by this user that have been read
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mBook ->
                    (mBook.userId == currentUser?.uid)
                }
            } else {
                emptyList()
            }

            Column {

                Row {

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Person,
                            contentDescription = "icon"
                        )
                    }

                    //prakhar@me.com -> prakhar
                    Text(
                        text = "Hi, ${
                            currentUser?.email.toString()
                                .split("@")[0].uppercase(Locale.getDefault())
                        }"
                    )

                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    shape = CircleShape,
                    elevation = 5.dp
                ) {

                    val readBooksList: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }

                        } else {
                            emptyList()
                        }

                    val readingBooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    Column(
                        modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Your Stats", style = MaterialTheme.typography.h5)
                        Divider()
                        Text(text = "You're reading: ${readingBooks.size} books")
                        Text(text = "You've read: ${readBooksList.size} books")
                    }

                }

            }

        }

    }

}
