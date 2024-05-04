package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.AuthorRequestTo
import com.github.hummel.dc.lab1.dto.request.AuthorRequestToId
import com.github.hummel.dc.lab1.dto.response.AuthorResponseTo
import com.github.hummel.dc.lab1.repository.AuthorsRepository
import com.github.hummel.dc.lab1.service.AuthorService

class AuthorServiceImpl(
	private val repository: AuthorsRepository
) : AuthorService {
	override suspend fun create(requestTo: AuthorRequestTo?): AuthorResponseTo? {
		val id = repository.getNextId() ?: return null
		val bean = requestTo?.toBean(id) ?: return null
		val result = repository.create(bean.id, bean) ?: return null

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
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}
}