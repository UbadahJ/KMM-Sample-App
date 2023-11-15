package io.ubadah.art


import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE

fun configureLogger() {
    Napier.base(DebugAntilog())
}

internal data object KoinLogger : Logger() {
    override fun display(level: Level, msg: MESSAGE) {
        Napier.log(LogLevel.valueOf(level.name), tag = "Koin", message = msg)
    }
}
