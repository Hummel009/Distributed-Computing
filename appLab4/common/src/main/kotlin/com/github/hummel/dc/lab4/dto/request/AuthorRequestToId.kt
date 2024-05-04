package com.github.hummel.dc.lab4.dto.request

import com.github.hummel.dc.lab4.bean.Author
import kotlinx.serialization.Serializable

@Serializable
data class AuthorRequestToId(
	private val id: Long,
	private val login: String,
	private val password: String,
	private val firstname: String,
	private val lastname: String
) {
	fun toBean(): Author = Author(id, login, password, firstname, lastname)

	init {
		require(login.length in 2..64)
		require(password.length in 8..128)
		require(firstname.length in 2..64)
		require(lastname.length in 2..64)
	}
}