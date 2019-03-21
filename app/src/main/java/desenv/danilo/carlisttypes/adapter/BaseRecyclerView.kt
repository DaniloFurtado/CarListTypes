package desenv.danilo.carlisttypes.adapter



import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var hasRecyclerAtached = false

    fun getHasRecyclerAtached(): Boolean {
        return hasRecyclerAtached
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        hasRecyclerAtached = true
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        hasRecyclerAtached = false
    }
}