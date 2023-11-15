pluginManagement {
    dependencyResolutionManagement {
        versionCatalogs {
            create("internal") {
                from(files("gradle/internal.versions.toml"))
            }
        }
    }

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

val props = java.util.Properties().apply {
    runCatching {
        file("local.properties").inputStream().use {
            load(it)
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://repo.repsy.io/mvn/vyro/internal") {
            credentials {
                username = System.getenv("REPSY_USERNAME") ?: props.getProperty("repsyUsername")
                password = System.getenv("REPSY_PASSWORD") ?: props.getProperty("repsyPassword")
            }
        }
        mavenLocal()
    }
}

rootProject.name = "Art"
include(":androidApp")
include(":shared")

include(":shared:art")
include(":shared:art:entities")
include(":shared:art:domain")
include(":shared:art:data")
include(":shared:art:local")
include(":shared:art:remote")
