package com.github.hummel.dc.lab5

import com.datastax.oss.driver.api.core.CqlSession
import com.github.hummel.dc.lab5.controller.configureRouting
import com.github.hummel.dc.lab5.module.appModule
import com.github.hummel.dc.lab5.module.dataModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.doublereceive.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import org.koin.ktor.plugin.Koin
import java.net.InetSocketAddress
import java.time.Duration
import java.util.*
import kotlin.concurrent.thread

private lateinit var consumer: KafkaConsumer<String, String>

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
	configureKafka()
}

fun configureKafka() {
	val bootstrapServers = "localhost:9092"
	val topic = "app"
	val groupId = "app-id"

	val consumerProps = Properties()
	consumerProps[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
	consumerProps[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
	consumerProps[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java.name
	consumerProps[ConsumerConfig.GROUP_ID_CONFIG] = groupId

	consumer = KafkaConsumer<String, String>(consumerProps)
	consumer.subscribe(listOf(topic))

	thread {
		while (true) {
			val records = consumer.poll(Duration.ofMillis(100))
			records.forEach { println(it.value()) }
		}
	}
}