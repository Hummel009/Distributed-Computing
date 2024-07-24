package com.github.hummel.dc.lab3.controller.routing

import com.github.hummel.dc.lab3.controller.respond
import com.github.hummel.dc.lab3.dto.request.IssueRequestTo
import com.github.hummel.dc.lab3.dto.request.IssueRequestToId
import com.github.hummel.dc.lab3.service.IssueService
import com.github.hummel.dc.lab3.util.Response
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
	}
}