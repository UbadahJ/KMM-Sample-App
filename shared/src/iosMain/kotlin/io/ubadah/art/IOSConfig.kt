package io.ubadah.art

import org.koin.core.context.startKoin

class IOSConfig {
    fun koin() = apply {
        startKoin {
            defaultConfig()
            modules(dependencies, ios)
        }
    }

    fun logger() = apply {
        configureLogger()
    }

    fun build() {}
}