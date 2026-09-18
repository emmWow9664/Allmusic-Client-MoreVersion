rootProject.name = "AllmusicClientMoreVersion"

include(":codec")
include(":client")
include(":client:fabric_1_21_1")
include(":client:fabric_1_21_2")
include(":client:fabric_1_21_5")
include(":client:fabric_1_21_7")
include(":client:fabric_1_21_10")
include(":client:fabric_26_1_2")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.architectury.dev/")
        maven("https://nexus.gtnewhorizons.com/repository/public/")
    }
}