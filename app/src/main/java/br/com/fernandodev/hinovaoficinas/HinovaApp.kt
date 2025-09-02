package br.com.fernandodev.hinovaoficinas

import android.app.Application
import br.com.fernandodev.hinovaoficinas.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HinovaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@HinovaApp)
            modules(appModules)
        }
    }
}
