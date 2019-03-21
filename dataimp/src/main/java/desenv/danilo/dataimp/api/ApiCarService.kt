package desenv.danilo.dataimp.api

import desenv.danilo.data.data.VehAvailRSCore
import io.reactivex.Single
import retrofit2.http.GET

interface ApiCarService {


    @GET("v2/5c92f6da3200000a626bd145")
    fun getAllUser(): Single<VehAvailRSCore>


}