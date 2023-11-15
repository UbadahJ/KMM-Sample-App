package io.ubadah.art.data.local


interface IO {
    fun save(data: ByteArray, fileName: String? = null): String
}