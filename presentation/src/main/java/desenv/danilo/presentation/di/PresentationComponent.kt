package desenv.danilo.presentation.di

import dagger.Component
import desenv.danilo.presentation.CarListViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [PresentationModule::class])
interface PresentationComponent {

    fun injectCarListViewModel(): CarListViewModel
}