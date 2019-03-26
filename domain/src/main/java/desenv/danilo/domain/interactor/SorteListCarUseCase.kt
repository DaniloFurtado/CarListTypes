package desenv.danilo.domain.interactor

import desenv.danilo.domain.ParamSortListCar
import desenv.danilo.domain.SingleUseCase
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.modelbinding.TypeSortList
import desenv.danilo.modelbinding.VehicleBind
import io.reactivex.Single
import javax.inject.Inject

class SorteListCarUseCase @Inject constructor(
    schedulerProvider: BaseSchedulerProvider
) : SingleUseCase<List<VehicleBind>, ParamSortListCar?>(schedulerProvider) {
    override fun buildSingleUseCase(params: ParamSortListCar?): Single<List<VehicleBind>> {
        return if (params != null && params.listVehicleBind.isNotEmpty()) {

            val listSorted = if (TypeSortList.LOWEST_PRICE == params.typeSortlist) {
                sortListCarLowest(params.listVehicleBind)
            } else {
                sortListCarHighest(params.listVehicleBind)
            }

            Single.just(listSorted)
        } else {
            Single.error(IllegalArgumentException("Parameter must be not null"))
        }
    }

    private fun sortListCarLowest(vehicles: List<VehicleBind>): List<VehicleBind> {
        return vehicles.sortedBy {
            it.rateTotalAmount.toDouble()
        }
    }


    private fun sortListCarHighest(vehicles: List<VehicleBind>): List<VehicleBind> {
        return vehicles.sortedBy {
            it.rateTotalAmount.toDouble()
        }.asReversed()
    }
}

