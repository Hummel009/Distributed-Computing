package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.IssueRequestTo
import com.github.hummel.dc.lab1.dto.request.IssueRequestToId
import com.github.hummel.dc.lab1.dto.response.IssueResponseTo
import com.github.hummel.dc.lab1.repository.IssuesRepository
import com.github.hummel.dc.lab1.service.IssueService
import java.sql.Timestamp

class IssueServiceImpl(
	private val repository: IssuesRepository
) : IssueService {
	override suspend fun create(requestTo: IssueRequestTo?): IssueResponseTo? {
		val created = Timestamp(System.currentTimeMillis())
		val modified = Timestamp(System.currentTimeMillis())

		val id = repository.getNextId() ?: return null
		val bean = requestTo?.toBean(id, created, modified) ?: return null
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = repository.deleteById(id)

	override suspend fun getAll(): List<IssueResponseTo> {
		val result = repository.getAll()

		return result.filterNotNull().map { it.toResponse() }
	}

	override suspend fun getById(id: Long): IssueResponseTo? {
		val result = repository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(requestTo: IssueRequestToId?): IssueResponseTo? {
		val created = Timestamp(System.currentTimeMillis())
		val modified = Timestamp(System.currentTimeMillis())

		val bean = requestTo?.toBean(created, modified) ?: return null
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}
}