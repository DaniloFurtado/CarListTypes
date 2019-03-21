package desenv.danilo.presentation.data

import desenv.danilo.modelbinding.AvaibleCoreBind
import desenv.danilo.modelbinding.VehicleBind
import desenv.danilo.modelbinding.VendorBind

object DataFactoryCar {
    fun dummyListUser() = AvaibleCoreBind("","","","", listOf(
        VehicleBind("dsafds", true,"", "sadfdfa", "4", "rewqrfdassd", "45454", VendorBind("adsfsda"), "sdafdsa")
    ))

}