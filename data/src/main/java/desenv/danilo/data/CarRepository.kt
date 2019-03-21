package desenv.danilo.data

import desenv.danilo.data.data.VehAvailRSCore
import io.reactivex.Single

interface CarRepository {

    fun getAllCar(): Single<VehAvailRSCore>
}