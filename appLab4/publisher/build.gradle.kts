import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
	id("org.jetbrains.kotlin.jvm")
	id("org.jetbrains.kotlin.plugin.serialization")
	id("io.ktor.plugin")
}

group = "com.github.hummel"
version = LocalDate.now().format(DateTimeFormatter.ofPattern("yy.MM.dd"))

dependencies {
	implementation(project(":appLab4:common"))

	implementation("org.postgresql:postgresql:42.7.3")
	implementation("com.h2database:h2:2.2.224")

	implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
	implementation("io.ktor:ktor-server-content-negotiation-jvm")

	implementation("io.ktor:ktor-server-core-jvm")
	implementation("io.ktor:ktor-server-freemarker-jvm")
	implementation("io.ktor:ktor-server-netty-jvm")
	implementation("io.ktor:ktor-server-double-receive")

	implementation("io.insert-koin:koin-ktor:3.6.0-wasm-alpha2")
	implementation("io.insert-koin:koin-logger-slf4j:3.6.0-wasm-alpha2")

	implementation("io.ktor:ktor-client-core:2.3.8")
	implementation("io.ktor:ktor-client-cio:2.3.8")
	implementation("io.ktor:ktor-client-logging:2.3.8")
	implementation("io.ktor:ktor-client-content-negotiation:2.3.8")

	implementation("org.apache.kafka:kafka-clients:3.7.0")
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(11)
	}
}

application {
	mainClass = "com.github.hummel.dc.lab4.PublisherKt"

	val isDevelopment = project.ext.has("development")
	applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}