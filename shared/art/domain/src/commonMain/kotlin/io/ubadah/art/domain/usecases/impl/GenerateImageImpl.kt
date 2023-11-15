package io.ubadah.art.domain.usecases.impl

import io.ubadah.art.domain.repositories.ImageRepository
import io.ubadah.art.domain.usecases.GenerateImage
import io.ubadah.art.entities.Image

internal class GenerateImageImpl(private val repo: ImageRepository): GenerateImage() {
    override suspend fun generate(prompt: String): Image = repo.generate(prompt)
}