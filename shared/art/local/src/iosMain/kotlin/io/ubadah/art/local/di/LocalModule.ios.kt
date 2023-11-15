package io.ubadah.art.local.di

import io.ubadah.art.data.local.IO
import io.ubadah.art.entities.annotations.InternalArtApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@InternalArtApi
internal actual fun dependency(): Module = module {
    singleOf(::IOImpl) bind IO::class
}