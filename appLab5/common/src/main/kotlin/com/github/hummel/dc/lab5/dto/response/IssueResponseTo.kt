package com.github.hummel.dc.lab5.dto.response

import com.github.hummel.dc.lab5.util.TimeStampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class IssueResponseTo(
	private val id: Long?,
	private val authorId: Long,
	private val title: String,
	private val content: String,
	@Serializable(TimeStampSerializer::class) private val created: Timestamp,
	@Serializable(TimeStampSerializer::class) private val modified: Timestamp
)