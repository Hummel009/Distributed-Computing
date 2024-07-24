package com.github.hummel.dc.lab3.controller.routing

import com.github.hummel.dc.lab3.dto.request.MessageRequestTo
import com.github.hummel.dc.lab3.dto.request.MessageRequestToId
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

internal fun Route.messagesRouting() {
	val client = HttpClient(CIO) {
		install(ContentNegotiation) {
			json()
		}
	}

	route("/messages") {
		checkMessages(client)

		createMessage(client)
		deleteMessage(client)
		updateMessage(client)
		getMessage(client)
	}
}

private fun Route.checkMessages(client: HttpClient) {
	get {
		call.respond(
			client.get("http://0.0.0.0:24130/api/v1.0/messages").bodyAsText()
		)
	}
}

private fun Route.createMessage(client: HttpClient) {
	post {
		val body = call.receive<MessageRequestTo>()
		val result = client.post("http://localhost:24130/api/v1.0/messages") {
			contentType(ContentType.Application.Json)
			setBody(body)
		}
		call.respond(
			status = result.status, message = result.bodyAsText()
		)
	}
}

private fun Route.getMessage(client: HttpClient) {
	get("/{id?}") {
		val id = call.parameters["id"]
		val result = client.get("http://localhost:24130/api/v1.0/messages/$id")
		call.respond(
			status = result.status, message = result.bodyAsText()
		)
	}
}

private fun Route.deleteMessage(client: HttpClient) {
	delete("/{id?}") {
		val id = call.parameters["id"]
		val result = client.delete("http://localhost:24130/api/v1.0/messages/$id")
		call.respond(
			status = result.status, message = result.bodyAsText()
		)
	}
}

private fun Route.updateMessage(client: HttpClient) {
	put {
		val body = call.receive<MessageRequestToId>()
		val result = client.put("http://localhost:24130/api/v1.0/messages") {
			contentType(ContentType.Application.Json)
			setBody(body)
		}
		call.respond(
			status = result.status, message = result.bodyAsText()
		)
	}
}