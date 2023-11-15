package io.ubadah.art.domain.di

import io.ubadah.art.domain.usecases.GenerateImage
import io.ubadah.art.domain.usecases.impl.GenerateImageImpl
import io.ubadah.art.entities.annotations.InternalArtApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@InternalArtApi
val domainModule = module {
    singleOf(::GenerateImageImpl) bind GenerateImage::class
}
