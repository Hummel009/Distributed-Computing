package com.github.hummel.dc.lab1

import com.github.hummel.dc.lab1.controller.configureRouting
import com.github.hummel.dc.lab1.module.appModule
import com.github.hummel.dc.lab1.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin

fun main() {
	embeddedServer(Netty, port = 24110, module = Application::module).start(wait = true)
}

fun Application.module() {
	install(DoubleReceive)
	install(Koin) {
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
}