package com.github.hummel.dc.lab5.controller.routing

import com.github.hummel.dc.lab5.controller.respond
import com.github.hummel.dc.lab5.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab5.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab5.sendViaKafka
import com.github.hummel.dc.lab5.service.AuthorService
import com.github.hummel.dc.lab5.util.Response
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authorsRouting() {
	val authorsService by inject<AuthorService>()

	route("/authors") {
		checkAuthors(authorsService)

		createAuthor(authorsService)
		deleteAuthor(authorsService)
		updateAuthor(authorsService)
		getAuthor(authorsService)
	}
}

private fun Route.checkAuthors(authorsService: AuthorService) {
	get {
		val authors = authorsService.getAll()

		respond(isCorrect = {
			authors.isNotEmpty()
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, authors
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.OK, Response(HttpStatusCode.OK.value)
			)
		})

		sendViaKafka("From Publisher: Authors GET")
	}
}

private fun Route.createAuthor(authorsService: AuthorService) {
	post {
		val authorRequestTo = try {
			call.receive<AuthorRequestTo>()
		} catch (_: Exception) {
			null
		}

		val author = authorsService.create(authorRequestTo)

		respond(isCorrect = {
			author != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.Created, author ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.Forbidden, Response(HttpStatusCode.Forbidden.value)
			)
		})

		sendViaKafka("From Publisher: Authors POST")
	}
}

private fun Route.getAuthor(authorsService: AuthorService) {
	get("/{id?}") {
		val id = call.parameters["id"] ?: return@get call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val author = authorsService.getById(id.toLong())

		respond(isCorrect = {
			author != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, author ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Authors GET ID")
	}
}

private fun Route.deleteAuthor(authorsService: AuthorService) {
	delete("/{id?}") {
		val id = call.parameters["id"] ?: return@delete call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val author = authorsService.deleteById(id.toLong())

		respond(isCorrect = {
			author
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.NoContent, Response(HttpStatusCode.NoContent.value)
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Authors DELETE ID")
	}
}

private fun Route.updateAuthor(authorsService: AuthorService) {
	put {
		val authorRequestToId = try {
			call.receive<AuthorRequestToId>()
		} catch (_: Exception) {
			null
		}

		val author = authorsService.update(authorRequestToId)

		respond(isCorrect = {
			author != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, author ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Authors PUT")
	}
}