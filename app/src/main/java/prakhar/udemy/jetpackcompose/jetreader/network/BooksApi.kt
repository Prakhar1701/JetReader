package prakhar.udemy.jetpackcompose.jetreader.network

import prakhar.udemy.jetpackcompose.jetreader.model.Books
import prakhar.udemy.jetpackcompose.jetreader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    @GET("volumes")
    suspend fun getAllBooks(@Query("q") query: String): Books

    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}