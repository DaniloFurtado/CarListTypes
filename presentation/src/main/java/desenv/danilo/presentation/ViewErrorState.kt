package desenv.danilo.presentation

class ViewErrorState(
    val type: TypeExibe,
    val error: Throwable? = null,
    val idMessage: Int = 0,
    val retry: Boolean = false
) {
    enum class TypeExibe {
        SNACK, TOAST, ALERT
    }
}