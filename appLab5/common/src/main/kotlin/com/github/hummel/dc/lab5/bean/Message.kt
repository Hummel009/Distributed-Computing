package com.github.hummel.dc.lab5.bean

import com.github.hummel.dc.lab5.dto.response.MessageResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Message(
	val id: Long?, val country: String?, val issueId: Long, val content: String
) {
	fun toResponse(): MessageResponseTo = MessageResponseTo(id, country, issueId, content)
}