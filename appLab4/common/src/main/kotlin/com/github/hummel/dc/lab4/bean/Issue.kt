package com.github.hummel.dc.lab4.bean

import com.github.hummel.dc.lab4.dto.response.IssueResponseTo
import com.github.hummel.dc.lab4.util.TimeStampSerializer
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class Issue(
	val id: Long?,
	val authorId: Long,
	val title: String,
	val content: String,
	@Serializable(TimeStampSerializer::class) val created: Timestamp,
	@Serializable(TimeStampSerializer::class) val modified: Timestamp
) {
	fun toResponse(): IssueResponseTo = IssueResponseTo(id, authorId, title, content, created, modified)
}