package com.github.hummel.dc.lab3.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseTo(
	private val id: Long?, private val country: String?, private val issueId: Long, private val content: String
)