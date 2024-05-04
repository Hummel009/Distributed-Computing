package com.github.hummel.dc.lab2.service.impl

import com.github.hummel.dc.lab2.dto.request.MessageRequestTo
import com.github.hummel.dc.lab2.dto.request.MessageRequestToId
import com.github.hummel.dc.lab2.dto.response.MessageResponseTo
import com.github.hummel.dc.lab2.repository.MessagesRepository
import com.github.hummel.dc.lab2.service.MessageService

class MessageServiceImpl(
	private val repository: MessagesRepository
) : MessageService {
	override suspend fun create(requestTo: MessageRequestTo?): MessageResponseTo? {
		val bean = requestTo?.toBean(null) ?: return null
		val id = repository.create(bean) ?: return null
		val result = bean.copy(id = id)

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = repository.deleteById(id)

	override suspend fun getAll(): List<MessageResponseTo> {
		val result = repository.getAll()

		return result.filterNotNull().map { it.toResponse() }
	}

	override suspend fun getById(id: Long): MessageResponseTo? {
		val result = repository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(requestTo: MessageRequestToId?): MessageResponseTo? {
		val bean = requestTo?.toBean() ?: return null

		if (!repository.update(bean)) {
			throw Exception("Exception during item updating!")
		}

		val result = bean.copy()

		return result.toResponse()
	}
}