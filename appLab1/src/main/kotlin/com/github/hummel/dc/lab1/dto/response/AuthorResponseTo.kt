package com.github.hummel.dc.lab1.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponseTo(
	private val id: Long,
	private val login: String,
	private val password: String,
	private val firstname: String,
	private val lastname: String
)