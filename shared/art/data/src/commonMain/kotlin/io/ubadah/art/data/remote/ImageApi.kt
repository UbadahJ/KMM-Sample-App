package io.ubadah.art.data.remote

import io.ubadah.art.entities.Image

interface ImageApi {
    suspend fun generate(prompt: String): ByteArray
}