package io.ubadah.art.data.repositories

import io.ubadah.art.data.local.IO
import io.ubadah.art.data.remote.ImageApi
import io.ubadah.art.domain.repositories.ImageRepository
import io.ubadah.art.entities.Image

class ImageRepositoryImpl(
    private val api: ImageApi,
    private val io: IO
): ImageRepository {
    override suspend fun generate(prompt: String): Image {
        val bytes = api.generate(prompt)
        return Image(io.save(bytes))
    }
}