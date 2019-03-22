package desenv.danilo.carlisttypes

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

class CarApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }

}