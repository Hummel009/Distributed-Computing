package com.github.hummel.dc.lab3

import com.datastax.driver.core.Cluster
import com.github.hummel.dc.lab3.controller.configureRouting
import com.github.hummel.dc.lab3.module.appModule
import com.github.hummel.dc.lab3.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin

fun main() {
	embeddedServer(Netty, port = 24130, module = Application::discussion).start(wait = true)
}

fun Application.discussion() {
	install(DoubleReceive)
	install(Koin) {
		dataModule.single<Cluster> {
			Cluster.builder().withoutMetrics().addContactPoints("127.0.0.1").build()
		}
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
}