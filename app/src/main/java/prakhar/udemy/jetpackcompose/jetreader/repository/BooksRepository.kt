package prakhar.udemy.jetpackcompose.jetreader.repository

import prakhar.udemy.jetpackcompose.jetreader.network.BooksApi
import javax.inject.Inject

class BooksRepository @Inject constructor(private val api: BooksApi) {
}