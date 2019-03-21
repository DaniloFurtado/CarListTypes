package desenv.danilo.presentation

class ViewState<D>(
    val status: Status,
    val data: D? = null
) {
    enum class Status {
        LOADING, SUCCESS
    }
}