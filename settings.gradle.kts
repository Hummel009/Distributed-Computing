pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
	}
}

plugins {
	id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":appLab1")
include(":appLab2")
include(":appLab3:common")
include(":appLab3:discussion")
include(":appLab3:publisher")
include(":appLab4:common")
include(":appLab4:discussion")
include(":appLab4:publisher")
include(":appLab5:common")
include(":appLab5:discussion")
include(":appLab5:publisher")