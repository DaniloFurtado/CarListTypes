package desenv.danilo.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import desenv.danilo.domain.ParamSortListCar
import desenv.danilo.domain.interactor.ListCarUseCase
import desenv.danilo.domain.interactor.SorteListCarUseCase
import desenv.danilo.modelbinding.AvaibleCoreBind
import desenv.danilo.modelbinding.TypeSortList
import desenv.danilo.modelbinding.VehicleBind
import javax.inject.Inject

class CarListViewModel @Inject constructor(
    private val listUserUseCase: ListCarUseCase,
    private val sorteListCarUseCase: SorteListCarUseCase
): BaseViewModel() {

    private val stateListUser: MutableLiveData<ViewState<AvaibleCoreBind>> = MutableLiveData()
    private val stateListChangedSort: MutableLiveData<List<VehicleBind>> = MutableLiveData()
    private var typeSortList = TypeSortList.LOWEST_PRICE

    fun getTypeSortList(): TypeSortList {
        return typeSortList
    }

    fun getStateListChangedSort(): MutableLiveData<List<VehicleBind>> {
        return stateListChangedSort
    }

    fun getStateListUser(): MutableLiveData<ViewState<AvaibleCoreBind>> {
        return stateListUser
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        listCars()
    }

    fun listCars(){
        listUserUseCase.executeUseCase(null,
            (this::listUserResult),
            (this::errorHandle),
            onSubscribe = (this::showProgress))
    }

    fun showProgress(){
        stateListUser.postValue(ViewState(ViewState.Status.LOADING))
    }

    private fun listUserResult(avaibleCoreBind: AvaibleCoreBind){
        stateListUser.postValue(ViewState(ViewState.Status.SUCCESS, data = avaibleCoreBind))
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        listUserUseCase.dispose()
        sorteListCarUseCase.dispose()
    }

    fun sortByLowestPrice(vehicles: List<VehicleBind>?, callback: (List<VehicleBind>) -> Unit) {
        typeSortList = TypeSortList.LOWEST_PRICE
        val paramSorteList = ParamSortListCar(typeSortList, vehicles!!)
        sorteListCarUseCase.executeUseCase(
            paramSorteList,
            {
                callback.invoke(it)
            },
            (this::errorHandle)
        )
    }

    fun sortByHighestPrice(vehicles: List<VehicleBind>?, callback: (List<VehicleBind>) -> Unit) {
        typeSortList = TypeSortList.HIGHEST_PRICE
        val paramSorteList = ParamSortListCar(typeSortList, vehicles!!)
        sorteListCarUseCase.executeUseCase(
            paramSorteList,
            {
                callback.invoke(it)
            },
            (this::errorHandle)
        )
    }

}