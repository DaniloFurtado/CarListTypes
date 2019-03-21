package desenv.danilo.carlisttypes.di

import dagger.Module
import dagger.Provides
import desenv.danilo.carlisttypes.adapter.CarListAdatper
import desenv.danilo.presentation.CarVmFactory
import javax.inject.Singleton


@Module
class AppModule {
    @Singleton
    @Provides
    fun providesVmCarFactory() = CarVmFactory()

    @Provides
    @Singleton
    fun providesCarListAdapter()= CarListAdatper(listOf())

}