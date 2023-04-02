package prakhar.udemy.jetpackcompose.jetreader.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import prakhar.udemy.jetpackcompose.jetreader.components.ReaderAppBar
import prakhar.udemy.jetpackcompose.jetreader.model.MBook
import prakhar.udemy.jetpackcompose.jetreader.navigation.ReaderScreens

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "JetReader", navController = navController)
    },
        floatingActionButton = {
            FABContent {}
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


@Composable
fun ReadingRightNowArea(
    listOfBooks: List<MBook>,
    navController: NavController
) {
}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label: String
) {
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(
                text = label,
                fontSize = 19.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Normal,
                textAlign = TextAlign.Left
            )
        }
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

@Preview
@Composable
fun HomeContent(navController: NavController = NavController(LocalContext.current)) {
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) {
        //prakhar@me.com -> prakhar
        FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
    } else "N/A"
    Column(
        Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
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
    }
}