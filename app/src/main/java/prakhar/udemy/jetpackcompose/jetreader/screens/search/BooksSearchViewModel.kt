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

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {

            if (query.isEmpty()) {
                return@launch
            }

            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                    }
                    is Resource.Error -> {
                        Log.d("Network", "searchBooks: Failed getting books...")
                    }
                    else -> {}
                }
            } catch (exception: Exception) {
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }

        }
    }

}