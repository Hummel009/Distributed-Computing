package com.github.hummel.dc.lab5.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MessageResponseTo(
	private val id: Long?, private val country: String?, private val issueId: Long, var content: String?
)