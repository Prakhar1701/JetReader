package prakhar.udemy.jetpackcompose.jetreader.repository

import prakhar.udemy.jetpackcompose.jetreader.data.Resource
import prakhar.udemy.jetpackcompose.jetreader.model.Item
import prakhar.udemy.jetpackcompose.jetreader.network.BooksApi
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api: BooksApi) {
    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {

        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(searchQuery).items

            if (itemList.isNotEmpty()) Resource.Loading(data = false)

            Resource.Success(data = itemList)
        } catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }

    }

    suspend fun getBookInfo(bookId: String): Resource<Item> {

        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        } catch (exception: Exception) {
            return Resource.Error(message = exception.message.toString())
        }

        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}
