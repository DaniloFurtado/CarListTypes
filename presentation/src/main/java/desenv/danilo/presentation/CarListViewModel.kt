package desenv.danilo.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import desenv.danilo.domain.interactor.ListCarUseCase
import desenv.danilo.modelbinding.AvaibleCoreBind
import javax.inject.Inject

class CarListViewModel @Inject constructor(
    private val listUserUseCase: ListCarUseCase
): BaseViewModel() {

    private val stateListUser: MutableLiveData<ViewState<AvaibleCoreBind>> = MutableLiveData()

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
    }

}