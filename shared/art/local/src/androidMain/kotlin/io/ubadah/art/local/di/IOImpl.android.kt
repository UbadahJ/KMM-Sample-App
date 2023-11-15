package io.ubadah.art.local.di

import android.content.Context
import io.ubadah.art.data.local.IO
import java.io.File

class IOImpl(private val context: Context) : IO {
    override fun save(data: ByteArray, fileName: String?): String {
        return File(context.cacheDir, fileName ?: "image.png")
            .apply { writeBytes(data) }
            .absolutePath
    }
}

