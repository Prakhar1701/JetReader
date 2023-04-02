package prakhar.udemy.jetpackcompose.jetreader.model

data class MBook(                //Use var not val to Firebase work properly...
    var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    val notes: String? = null
)
