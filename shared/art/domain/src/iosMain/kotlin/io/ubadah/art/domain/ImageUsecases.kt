package io.ubadah.art.domain

import io.ubadah.art.domain.usecases.GenerateImage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object ImageUsecases : KoinComponent {
    val generateImage: GenerateImage by inject()
}