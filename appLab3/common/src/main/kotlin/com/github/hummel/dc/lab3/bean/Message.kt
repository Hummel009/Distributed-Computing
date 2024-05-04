package com.github.hummel.dc.lab3.bean

import com.github.hummel.dc.lab3.dto.response.MessageResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Message(
	val id: Long?, val country: String?, val issueId: Long, val content: String
) {
	fun toResponse(): MessageResponseTo = MessageResponseTo(id, country, issueId, content)
}