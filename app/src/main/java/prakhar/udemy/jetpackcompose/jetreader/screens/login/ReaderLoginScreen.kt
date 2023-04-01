package prakhar.udemy.jetpackcompose.jetreader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import prakhar.udemy.jetpackcompose.jetreader.components.EmailInput
import prakhar.udemy.jetpackcompose.jetreader.components.JetReaderLogo
import prakhar.udemy.jetpackcompose.jetreader.components.PasswordInput

@Composable
fun ReaderLoginScreen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            JetReaderLogo()
            UserForm(loading = false, isCreateAccount = false) { email, password ->
                Log.d("Form", "ReaderLoginScreen: $email $password")
            }
        }
    }
}

@Preview
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier                     //For keyboard to not overlap views:)
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() })

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })
    }
}
