package com.github.hummel.dc.lab2.dto.request

import com.github.hummel.dc.lab2.bean.Message
import kotlinx.serialization.Serializable

@Serializable
data class MessageRequestToId(
	private val id: Long, private val issueId: Long, private val content: String
) {
	fun toBean(): Message = Message(
		id, issueId, content
	)

	init {
		require(content.length in 4..2048)
	}
}