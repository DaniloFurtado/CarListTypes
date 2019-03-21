package desenv.danilo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desenv.danilo.presentation.di.DaggerPresentationComponent


class CarVmFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(CarListViewModel::class.java)  -> {
                return DaggerPresentationComponent.builder().build().injectCarListViewModel() as T
            }
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}