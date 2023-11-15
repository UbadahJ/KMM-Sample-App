package io.ubadah.art.data.di

import io.ubadah.art.data.repositories.ImageRepositoryImpl
import io.ubadah.art.domain.repositories.ImageRepository
import io.ubadah.art.entities.annotations.InternalArtApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@InternalArtApi
val dataModule = module {
    singleOf(::ImageRepositoryImpl) bind ImageRepository::class
}
