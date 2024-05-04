package com.github.hummel.dc.lab2.bean

import com.github.hummel.dc.lab2.dto.response.MessageResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Message(
	val id: Long?, val issueId: Long, val content: String
) {
	fun toResponse(): MessageResponseTo = MessageResponseTo(id, issueId, content)
}