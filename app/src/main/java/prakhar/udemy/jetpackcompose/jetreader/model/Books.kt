package prakhar.udemy.jetpackcompose.jetreader.model

data class Books(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)