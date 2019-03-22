package desenv.danilo.domain.converterbind

import desenv.danilo.data.data.VehAvailRSCore
import desenv.danilo.data.data.VehAvails
import desenv.danilo.data.data.Vendor
import desenv.danilo.domain.extentions.toFormaStringDate
import desenv.danilo.modelbinding.AvaibleCoreBind
import desenv.danilo.modelbinding.VehicleBind
import desenv.danilo.modelbinding.VendorBind

object CarConvert {

    fun fromDataAvaibleCore(veh: VehAvailRSCore): AvaibleCoreBind {

        return AvaibleCoreBind(
            veh.vehRentalCore.pickUpDateTime.toFormaStringDate(),
            veh.vehRentalCore.returnDateTime.toFormaStringDate(),
            veh.vehRentalCore.pickUpLocation.name,
            veh.vehRentalCore.returnLocation.name,
            getListOfCars(veh).toList())
    }

    private fun getListOfCars(veh: VehAvailRSCore): MutableList<VehicleBind> {
        val vehicles = mutableListOf<VehicleBind>()
        veh.vehVendorAvails.forEach {
            it.vehAvails.forEach { veich ->
                vehicles.add(fromDataVehicle(it.vendor, veich))
            }
        }
        return vehicles
    }


    fun fromDataVehicle(vendor: Vendor, vehiclesParam: VehAvails): VehicleBind {

            val vehicle = VehicleBind(vehiclesParam.status,
                vehiclesParam.vehicle.airConditionInd,
                vehiclesParam.vehicle.transmissionType,
                vehiclesParam.vehicle.fuelType,
                vehiclesParam.vehicle.passengerQuantity,
                vehiclesParam.vehicle.pictureURL,
                vehiclesParam.totalCharge.rateTotalAmount.toString(),
                VendorBind(vendor.name),
                vehiclesParam.vehicle.VehMakeModel.name
            )

        return vehicle
    }

}