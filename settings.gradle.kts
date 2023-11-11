pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GoogleMapSample"
include(":app")
include(":di")
include(":feature-map:map-data")
include(":feature-map:map-domain")
include(":feature-map:map-ui")
include(":feature-map:map-model")
include(":base")
