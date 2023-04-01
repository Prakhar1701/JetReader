package prakhar.udemy.jetpackcompose.jetreader.screens.login

data class LoadingState(val status: Status, val message: String? = null) {

    companion object {
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAILED = LoadingState(Status.FAILED)
        val LOADING = LoadingState(Status.LOADING)
        val IDLE = LoadingState(Status.IDLE)
    }

    enum class Status {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}
