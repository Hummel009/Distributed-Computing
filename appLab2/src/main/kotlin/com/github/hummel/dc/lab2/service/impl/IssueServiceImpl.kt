package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.dto.request.IssueRequestTo
import com.github.hummel.dc.lab2.dto.request.IssueRequestToId
import com.github.hummel.dc.lab2.dto.response.IssueResponseTo
import com.github.hummel.dc.lab2.repository.IssuesRepository
import com.github.hummel.dc.lab2.service.IssueService
import java.sql.Timestamp

class IssueServiceImpl(
	private val repository: IssuesRepository
) : IssueService {
	override suspend fun create(requestTo: IssueRequestTo?): IssueResponseTo? {
		val created = Timestamp(System.currentTimeMillis())
		val modified = Timestamp(System.currentTimeMillis())

		val bean = requestTo?.toBean(null, created, modified) ?: return null
		val id = repository.create(bean) ?: return null
		val result = bean.copy(id = id)

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

		if (!repository.update(bean)) {
			throw Exception("Exception during item updating!")
		}

		val result = bean.copy()

		return result.toResponse()
	}
}