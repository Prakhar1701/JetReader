package prakhar.udemy.jetpackcompose.jetreader.data

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    //If none of this makes sense, please look up "Kotlin Generics"
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
