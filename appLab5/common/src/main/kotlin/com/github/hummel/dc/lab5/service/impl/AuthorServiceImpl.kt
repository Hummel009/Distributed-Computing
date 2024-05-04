package com.github.hummel.dc.lab5.service.impl

import com.github.hummel.dc.lab5.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab5.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab5.dto.response.AuthorResponseTo
import com.github.hummel.dc.lab5.repository.AuthorsRepository
import com.github.hummel.dc.lab5.service.AuthorService

class AuthorServiceImpl(
	private val repository: AuthorsRepository
) : AuthorService {
	override suspend fun create(requestTo: AuthorRequestTo?): AuthorResponseTo? {
		val bean = requestTo?.toBean(null) ?: return null
		val id = repository.create(bean) ?: return null
		val result = bean.copy(id = id)

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = repository.deleteById(id)

	override suspend fun getAll(): List<AuthorResponseTo> {
		val result = repository.getAll()

		return result.filterNotNull().map { it.toResponse() }
	}

	override suspend fun getById(id: Long): AuthorResponseTo? {
		val result = repository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(requestTo: AuthorRequestToId?): AuthorResponseTo? {
		val bean = requestTo?.toBean() ?: return null

		if (!repository.update(bean)) {
			throw Exception("Exception during item updating!")
		}

		val result = bean.copy()

		return result.toResponse()
	}
}