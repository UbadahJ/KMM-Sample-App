package io.ubadah.art.android

import android.app.Application
import io.ubadah.art.configureLogger
import io.ubadah.art.startSharedKoin
import org.koin.android.ext.koin.androidContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        configureLogger()
        startSharedKoin {
            androidContext(this@App)
        }
    }
}