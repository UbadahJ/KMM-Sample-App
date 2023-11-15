package io.ubadah.art.remote.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ubadah.art.data.remote.ImageApi
import io.ubadah.art.entities.annotations.InternalArtApi
import io.ubadah.art.remote.ImageApiImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

@InternalArtApi
val remoteModule = module {
    single {
        Json { ignoreUnknownKeys = true }
    }
    single { httpClient(get()) }
    singleOf(::ImageApiImpl) bind ImageApi::class
}


private fun httpClient(json: Json) = HttpClient {
    install(ContentNegotiation) {
        json(json)
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.d(message = message, tag = "KtorClient")
            }
        }
    }
}
