package com.github.hummel.dc.lab3

import com.datastax.oss.driver.api.core.CqlSession
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
import java.net.InetSocketAddress

fun main() {
	embeddedServer(Netty, port = 24130, module = Application::discussion).start(wait = true)
}

fun Application.discussion() {
	install(DoubleReceive)
	install(Koin) {
		dataModule.single<CqlSession> {
			CqlSession.builder().addContactPoint(
				InetSocketAddress("127.0.0.1", 9042)
			).withLocalDatacenter("datacenter1").withKeyspace("distcomp").build()
		}
		modules(dataModule, appModule)
	}
	install(ContentNegotiation) {
		json()
	}
	configureRouting()
}