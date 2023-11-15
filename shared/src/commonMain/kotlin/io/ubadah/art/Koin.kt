package io.ubadah.art

import io.ubadah.art.KoinLogger
import io.ubadah.art.di.artModule
import org.koin.core.KoinApplication
import org.koin.dsl.module

internal val dependencies = module {
    includes(artModule())
}

internal fun KoinApplication.defaultConfig() {
    logger(KoinLogger)
}