package desenv.danilo.carlisttypes.di

import dagger.Component
import desenv.danilo.carlisttypes.CarListActivity
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectList(activity: CarListActivity)
}