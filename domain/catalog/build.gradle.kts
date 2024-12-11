plugins {
    alias(libs.plugins.flixclusive.library)
    alias(libs.plugins.flixclusive.hilt)
}

android {
    namespace = "com.flixclusive.domain.catalog"
}

dependencies {
    api(projects.domain.tmdb)
    api(projects.domain.provider)

    implementation(projects.core.network)
}