package desenv.danilo.dataimp

import desenv.danilo.data.CarRepository
import desenv.danilo.data.data.VehAvailRSCore
import desenv.danilo.dataimp.api.ApiCarService
import io.reactivex.Single

class CarRepositoryImp(private val api: ApiCarService): CarRepository {
    override fun getAllCar(): Single<VehAvailRSCore> {
        return api.getAllUser()
    }



}