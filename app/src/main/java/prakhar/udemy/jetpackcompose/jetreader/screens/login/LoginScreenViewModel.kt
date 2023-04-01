package prakhar.udemy.jetpackcompose.jetreader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    //    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    //--------------------------------------------------------------------------------------------------
    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "FB",
                                "signInWithEmailAndPassword: Yayayay! ${task.result.toString()}"
                            )
                            home();
                        } else {
                            Log.d("FB", "signInWithEmailAndPassword: ${task.result.toString()}")
                        }
                    }

            } catch (ex: Exception) {
                Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
            }
        }
}
