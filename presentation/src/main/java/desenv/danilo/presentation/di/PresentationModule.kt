package desenv.danilo.presentation.di

import android.content.Context
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import desenv.danilo.data.CarRepository
import desenv.danilo.dataimp.CarRepositoryImp
import desenv.danilo.dataimp.api.APIClient
import desenv.danilo.dataimp.api.ApiCarService
import desenv.danilo.domain.executor.BaseSchedulerProvider
import desenv.danilo.presentation.executor.SchedulerProvider
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class PresentationModule {

    @Singleton
    @Provides
    fun providesUserRepository( apiUserService: ApiCarService): CarRepository {
        return CarRepositoryImp(apiUserService)
    }

    @Singleton
    @Provides
    fun providesSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit = APIClient.getAPIService()


    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiCarService = retrofit.create(ApiCarService::class.java)
}