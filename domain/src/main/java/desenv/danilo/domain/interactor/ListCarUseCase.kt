package desenv.danilo.domain.interactor

import desenv.danilo.data.CarRepository
import desenv.danilo.domain.SingleUseCase
import desenv.danilo.domain.converterbind.CarConvert
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.modelbinding.AvaibleCoreBind
import io.reactivex.Single
import javax.inject.Inject

open class ListCarUseCase @Inject constructor (
    private val repository: CarRepository,
    schedulerProvider: BaseSchedulerProvider
): SingleUseCase<AvaibleCoreBind, Unit>(schedulerProvider) {

    override fun buildSingleUseCase(params: Unit?): Single<AvaibleCoreBind> {
        return repository.getAllCar()
            .map {
                val avaibleCore = CarConvert.fromDataAvaibleCore(it)
                avaibleCore.vehicles = avaibleCore.vehicles.sortedWith(compareBy { it.rateTotalAmount.toDouble() })

                avaibleCore

            }
    }


}