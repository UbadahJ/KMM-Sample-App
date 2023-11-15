package io.ubadah.art.di

import io.ubadah.art.data.di.dataModule
import io.ubadah.art.domain.di.domainModule
import io.ubadah.art.entities.annotations.InternalArtApi
import io.ubadah.art.local.di.localModule
import io.ubadah.art.remote.di.remoteModule
import org.koin.dsl.module


@OptIn(InternalArtApi::class)
fun artModule() = module {
    includes(
        domainModule,
        dataModule,
        remoteModule,
        localModule,
    )
}
