package desenv.danilo.carlisttypes

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import desenv.danilo.carlisttypes.databinding.ActivityShowDetailsBinding
import desenv.danilo.modelbinding.VehicleBind
import kotlinx.android.synthetic.main.activity_show_details.*

class ShowDetailsActivity : BaseActivity() {

    companion object {

        const val VEHICLE_SELECTED = "selected_vehicle"
        const val  ID_TRANSITION = "id_transition"
    }

    private lateinit var binding: ActivityShowDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configTransition()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_details)
        startTransition()
        initToolbarBack(toolbar)
    }

    private fun startTransition() {
        if (avaliableTransition() && intent.extras != null) {
            val vehicleBind: VehicleBind? = intent.extras!!.getParcelable(VEHICLE_SELECTED)
            setPositionTransition()
            binding.itemCar = vehicleBind
            startPostponedEnterTransition()
        }
    }

    private fun setPositionTransition() {
        if (avaliableTransition()) {
            val positionTransition: Int? = intent.extras!!.getInt(ID_TRANSITION)
            if (positionTransition != null)
                imageUser.transitionName = positionTransition.toString()
        }
    }

    private fun configTransition() {
        if (avaliableTransition())
            postponeEnterTransition()
    }
}
