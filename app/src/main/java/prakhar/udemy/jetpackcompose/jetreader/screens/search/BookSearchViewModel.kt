package prakhar.udemy.jetpackcompose.jetreader.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import prakhar.udemy.jetpackcompose.jetreader.data.DataOrException
import prakhar.udemy.jetpackcompose.jetreader.model.Item
import prakhar.udemy.jetpackcompose.jetreader.repository.BooksRepository
import javax.inject.Inject


@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BooksRepository) :
    ViewModel() {
    private val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch() {
            if (query.isEmpty()) {
                return@launch
            }
            listOfBooks.value.loading = true
            listOfBooks.value = repository.getBooks(query)

            if (listOfBooks.value.data.toString().isNotEmpty()) {
                listOfBooks.value.loading = false
            }

            Log.d("DATA", "searchBooks: ${listOfBooks.value.data.toString()}")
        }
    }
}