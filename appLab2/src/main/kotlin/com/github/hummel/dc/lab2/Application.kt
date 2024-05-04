package com.github.hummel.dc.lab2

import com.github.hummel.dc.lab2.controller.configureRouting
import com.github.hummel.dc.lab2.module.appModule
import com.github.hummel.dc.lab2.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.koin.ktor.plugin.Koin
import java.sql.Connection
import java.sql.DriverManager

fun main() {
	embeddedServer(Netty, port = 24110, module = Application::module).start(wait = true)
}

fun Application.module() {
	install(DoubleReceive)
	install(Koin) {
		dataModule.single<Connection> {
			Class.forName("org.postgresql.Driver")
			DriverManager.getConnection("jdbc:postgresql://localhost:5432/distcomp", "postgres", "postgres")
		}
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
}