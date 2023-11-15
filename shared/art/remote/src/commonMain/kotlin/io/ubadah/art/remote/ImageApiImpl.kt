package io.ubadah.art.remote

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ubadah.art.data.remote.ImageApi

class ImageApiImpl(
    private val client: HttpClient
) : ImageApi {
    override suspend fun generate(prompt: String): ByteArray {
        val res = client.post("https://api.vyro.ai/v1/imagine/api/generations") {
            bearerAuth("vk-0Gzfvs95z1FCyUO4hD32CFsmL1xATuyJq4qVdc0RpnWUF6")
            setBody(MultiPartFormDataContent(formData {
                append("prompt", prompt)
                append("style_id", "21")
            }))
        }

        if (res.status != HttpStatusCode.OK) {
            throw Exception("Failed to generate image")
        }

        return res.body()
    }
}