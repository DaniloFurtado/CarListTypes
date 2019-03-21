package desenv.danilo.carlisttypes.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import desenv.danilo.carlisttypes.BR
import desenv.danilo.carlisttypes.databinding.ItemListCarBinding
import desenv.danilo.modelbinding.VehicleBind
import kotlinx.android.synthetic.main.item_list_car.view.*


class CarListAdatper(
    private var listCars: List<VehicleBind>
): BaseRecyclerView() {

    private var onItemClick: ((VehicleBind, Int, View) -> Unit)? = null

    fun setInilistenerClick(listenerCLick: (VehicleBind, Int, View) -> Unit){
        onItemClick = listenerCLick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListCarBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount() = listCars.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val car = listCars[position]
            holder.bind(car)
            holder.itemView.itemRoot.setOnClickListener {
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                     holder.itemView.imageUser.transitionName = position.toString()
                 }
                  onItemClick?.invoke(car, position, holder.itemView.imageUser)
            }
        }else{
            throw RuntimeException("Type ViewHolder not implemented")
        }
    }

    inner class MyViewHolder(private val binding: ItemListCarBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(carBind: VehicleBind) {
            binding.setVariable(BR.itemCar, carBind)
            binding.executePendingBindings()
        }

    }

    fun setItems(items: List<VehicleBind>) {
        listCars = items
        notifyDataChanged()
    }

    // Verifica se pode notificar a mudança, se já foi adicionado a RecyclerView
    private fun notifyDataChanged() {
        if (getHasRecyclerAtached()) {
            notifyDataSetChanged()
        }
    }

}