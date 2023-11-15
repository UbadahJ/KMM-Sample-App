package io.ubadah.art.local.di

import io.ubadah.art.data.local.IO
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.writeToFile

class IOImpl : IO {
    override fun save(data: ByteArray, fileName: String?): String {
        NSURL(fileURLWithPath = NSTemporaryDirectory()).let { url ->
            val path = url.path
            val file = if (fileName != null) {
                "$path/$fileName"
            } else {
                "$path/image.png"
            }

            data.toNSData().writeToFile(file, true)
            return file
        }
    }
}

@OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData = memScoped {
    val data = this@toNSData
    NSData.create(
        bytes = allocArrayOf(data),
        length = data.size.toULong()
    )
}