package com.github.hummel.dc.lab5.controller.routing

import com.github.hummel.dc.lab5.controller.respond
import com.github.hummel.dc.lab5.dto.request.MessageRequestTo
import com.github.hummel.dc.lab5.dto.request.MessageRequestToId
import com.github.hummel.dc.lab5.service.MessageService
import com.github.hummel.dc.lab5.util.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.messagesRouting() {
	val messagesService by inject<MessageService>()

	route("/messages") {
		checkMessages(messagesService)

		createMessage(messagesService)
		deleteMessage(messagesService)
		updateMessage(messagesService)
		getMessage(messagesService)
	}
}

private fun Route.checkMessages(messagesService: MessageService) {
	get {
		val messages = messagesService.getAll()

		respond(isCorrect = {
			messages.isNotEmpty()
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, messages
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.OK, Response(HttpStatusCode.OK.value)
			)
		})
	}
}

private fun Route.createMessage(messagesService: MessageService) {
	post {
		val messageRequestTo = try {
			call.receive<MessageRequestTo>()
		} catch (e: Exception) {
			null
		}

		val message = messagesService.create(messageRequestTo)

		respond(isCorrect = {
			message != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.Created, message ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})
	}
}

private fun Route.getMessage(messagesService: MessageService) {
	get("/{id?}") {
		val id = call.parameters["id"] ?: return@get call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val message = messagesService.getById(id.toLong())

		respond(isCorrect = {
			message != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, message ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})
	}
}

private fun Route.deleteMessage(messagesService: MessageService) {
	delete("/{id?}") {
		val id = call.parameters["id"] ?: return@delete call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val message = messagesService.deleteById(id.toLong())

		respond(isCorrect = {
			message
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.NoContent, Response(HttpStatusCode.NoContent.value)
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})
	}
}

private fun Route.updateMessage(messagesService: MessageService) {
	put {
		val messageRequestToId = try {
			call.receive<MessageRequestToId>()
		} catch (e: Exception) {
			null
		}

		val message = messagesService.update(messageRequestToId)

		respond(isCorrect = {
			message != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, message ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})
	}
}