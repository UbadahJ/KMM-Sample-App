package io.ubadah.art.domain.repositories

import io.ubadah.art.entities.Image

interface ImageRepository {
    suspend fun generate(prompt: String): Image
}