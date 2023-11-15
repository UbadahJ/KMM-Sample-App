package io.ubadah.art.local.di

import io.ubadah.art.entities.annotations.InternalArtApi
import org.koin.core.module.Module
import org.koin.dsl.module

@InternalArtApi
internal expect fun dependency(): Module

@InternalArtApi
val localModule = module {
    includes(dependency())
}
