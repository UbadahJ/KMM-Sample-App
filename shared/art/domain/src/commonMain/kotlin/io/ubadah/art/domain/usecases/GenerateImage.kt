package io.ubadah.art.domain.usecases

import io.ubadah.art.entities.Image

abstract class GenerateImage {
    abstract suspend fun generate(prompt: String): Image
}