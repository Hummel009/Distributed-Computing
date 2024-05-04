package com.github.hummel.dc.lab3

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
import java.sql.Connection
import java.sql.DriverManager

fun main() {
	embeddedServer(Netty, port = 24110, module = Application::publisher).start(wait = true)
}

fun Application.publisher() {
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