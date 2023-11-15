import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.detekt)
    id("maven-publish")
}

val dep = internal.kmm.art.get()
group = dep.group
version = dep.version ?: throw IllegalStateException("Version is not set")

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }

        publishLibraryVariants("debug", "release")
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "art"
            transitiveExport = true

            linkerOpts("-lsqlite3")
            export(project(":shared:art:entities"))
            export(project(":shared:art:domain"))
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":shared:art:entities"))
                api(project(":shared:art:domain"))
                implementation(project(":shared:art:remote"))
                implementation(project(":shared:art:data"))
                implementation(project(":shared:art:local"))

                implementation(libs.bundles.common)
                implementation(libs.bundles.di)
            }
        }
    }
}

android {
    namespace = "io.ubadah.art"
    compileSdk = libs.versions.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*.kotlin_module"
            excludes += "/META-INF/versions/**"
        }
    }
}

val props = Properties().apply {
    rootProject.file("local.properties").inputStream().use {
        load(it)
    }
}

configure<PublishingExtension> {
    publishing {
        repositories {
            maven(url = "https://repo.repsy.io/mvn/vyro/internal") {
                credentials {
                    username = props.getProperty("repsyUsername")
                    password = props.getProperty("repsyPassword")
                }
            }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    reportsDir = file("$buildDir/reports/detekt")

    dependencies {
        detektPlugins(libs.detekt.formatting)
    }
}