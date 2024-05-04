package com.github.hummel.dc.lab4.controller

import com.github.hummel.dc.lab4.controller.routing.authorsRouting
import com.github.hummel.dc.lab4.controller.routing.issuesRouting
import com.github.hummel.dc.lab4.controller.routing.messagesRouting
import com.github.hummel.dc.lab4.controller.routing.stickersRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
	routing {
		get("/") {
			call.respondText("Hello World!")
		}
		route("/api/v1.0") {
			authorsRouting()
			issuesRouting()
			messagesRouting()
			stickersRouting()
		}
	}
}

suspend fun respond(
	isCorrect: () -> Boolean, onCorrect: suspend () -> Unit, onIncorrect: suspend () -> Unit
) {
	if (isCorrect()) {
		onCorrect()
	} else {
		onIncorrect()
	}
}