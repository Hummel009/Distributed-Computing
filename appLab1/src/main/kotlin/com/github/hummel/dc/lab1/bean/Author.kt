package com.github.hummel.dc.lab1.bean

import com.github.hummel.dc.lab1.dto.response.AuthorResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Author(
	val id: Long, val login: String, val password: String, val firstname: String, val lastname: String
) {
	fun toResponse(): AuthorResponseTo = AuthorResponseTo(id, login, password, firstname, lastname)
}