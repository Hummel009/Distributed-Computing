package com.github.hummel.dc.lab5.controller.routing

import com.github.hummel.dc.lab5.controller.respond
import com.github.hummel.dc.lab5.dto.request.IssueRequestTo
import com.github.hummel.dc.lab5.dto.request.IssueRequestToId
import com.github.hummel.dc.lab5.sendViaKafka
import com.github.hummel.dc.lab5.service.IssueService
import com.github.hummel.dc.lab5.util.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.issuesRouting() {
	val issuesService by inject<IssueService>()

	route("/issues") {
		checkIssues(issuesService)

		createIssue(issuesService)
		deleteIssue(issuesService)
		updateIssue(issuesService)
		getIssue(issuesService)
	}
}

private fun Route.checkIssues(issuesService: IssueService) {
	get {
		val issues = issuesService.getAll()

		respond(isCorrect = {
			issues.isNotEmpty()
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, issues
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.OK, Response(HttpStatusCode.OK.value)
			)
		})

		sendViaKafka("From Publisher: Issues GET")
	}
}

private fun Route.createIssue(issuesService: IssueService) {
	post {
		val issueRequestTo = try {
			call.receive<IssueRequestTo>()
		} catch (e: Exception) {
			null
		}

		val issue = issuesService.create(issueRequestTo)

		respond(isCorrect = {
			issue != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.Created, issue ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.Forbidden, Response(HttpStatusCode.Forbidden.value)
			)
		})

		sendViaKafka("From Publisher: Issues POST")
	}
}

private fun Route.getIssue(issuesService: IssueService) {
	get("/{id?}") {
		val id = call.parameters["id"] ?: return@get call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val issue = issuesService.getById(id.toLong())

		respond(isCorrect = {
			issue != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, issue ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Issues GET ID")
	}
}

private fun Route.deleteIssue(issuesService: IssueService) {
	delete("/{id?}") {
		val id = call.parameters["id"] ?: return@delete call.respond(
			status = HttpStatusCode.BadRequest, message = Response(HttpStatusCode.BadRequest.value)
		)

		val issue = issuesService.deleteById(id.toLong())

		respond(isCorrect = {
			issue
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.NoContent, Response(HttpStatusCode.NoContent.value)
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Issues DELETE ID")
	}
}

private fun Route.updateIssue(issuesService: IssueService) {
	put {
		val issueRequestToId = try {
			call.receive<IssueRequestToId>()
		} catch (e: Exception) {
			null
		}

		val issue = issuesService.update(issueRequestToId)

		respond(isCorrect = {
			issue != null
		}, onCorrect = {
			call.respond(
				status = HttpStatusCode.OK, issue ?: return@respond
			)
		}, onIncorrect = {
			call.respond(
				status = HttpStatusCode.BadRequest, Response(HttpStatusCode.BadRequest.value)
			)
		})

		sendViaKafka("From Publisher: Issues PUT")
	}
}