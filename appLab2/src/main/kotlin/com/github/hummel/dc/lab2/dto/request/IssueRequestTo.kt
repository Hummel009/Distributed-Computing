package com.github.hummel.dc.lab2.dto.request

import com.github.hummel.dc.lab2.bean.Issue
import kotlinx.serialization.Serializable
import java.sql.Timestamp

@Serializable
data class IssueRequestTo(
	private val authorId: Long, private val title: String, private val content: String
) {
	fun toBean(id: Long?, created: Timestamp, modified: Timestamp): Issue = Issue(
		id, authorId, title, content, created, modified
	)

	init {
		require(title.length in 2..64)
		require(content.length in 4..2048)
	}
}