package io.ubadah.art

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal val android = module {}

fun startSharedKoin(action: KoinApplication.() -> Unit) {
    startKoin {
        defaultConfig()
        modules(dependencies, android)

        action()
    }
}