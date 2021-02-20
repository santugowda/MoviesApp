package com.moviesapp

import android.app.Application
import com.moviesapp.modules.omdbApiClientModule
import com.moviesapp.modules.omdbApiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                listOf(
                    omdbApiModule,
                    omdbApiClientModule
                )
            )
        }
    }

}