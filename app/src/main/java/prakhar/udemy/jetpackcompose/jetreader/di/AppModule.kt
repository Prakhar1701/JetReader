package prakhar.udemy.jetpackcompose.jetreader.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prakhar.udemy.jetpackcompose.jetreader.repository.BooksRepository
import prakhar.udemy.jetpackcompose.jetreader.network.BooksApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBooksRepository(api: BooksApi) = BooksRepository(api)

    @Singleton
    @Provides
    fun provideBooksApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(prakhar.udemy.jetpackcompose.jetreader.utils.Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}