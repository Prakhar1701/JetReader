package prakhar.udemy.jetpackcompose.jetreader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import prakhar.udemy.jetpackcompose.jetreader.data.Resource
import prakhar.udemy.jetpackcompose.jetreader.model.Item
import prakhar.udemy.jetpackcompose.jetreader.repository.BooksRepository
import javax.inject.Inject

@HiltViewModel
class BooksSearchViewModel @Inject constructor(private val repository: BooksRepository) :
    ViewModel() {
    var list: List<Item> by mutableStateOf(listOf())

    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {

//        isLoading = true //As default is already set to "true"

        viewModelScope.launch(Dispatchers.Default) {
            //"Dispatchers.Default" above may generate errors..., if it does remove it

            if (query.isEmpty()) {
                return@launch
            }

            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!

                        if (list.isNotEmpty()) isLoading = false

                    }
                    is Resource.Error -> {

                        isLoading = false

                        Log.d("Network", "searchBooks: Failed getting books...")
                    }
                    else -> {
                        isLoading = false
                    }
                }
            } catch (exception: Exception) {

                isLoading = false

                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }

        }
    }

}