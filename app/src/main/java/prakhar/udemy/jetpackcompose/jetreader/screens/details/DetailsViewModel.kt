package prakhar.udemy.jetpackcompose.jetreader.screens.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import prakhar.udemy.jetpackcompose.jetreader.data.Resource
import prakhar.udemy.jetpackcompose.jetreader.model.Item
import prakhar.udemy.jetpackcompose.jetreader.repository.BooksRepository
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BooksRepository) : ViewModel() {
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}