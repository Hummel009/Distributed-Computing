package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.MessageRequestTo
import com.github.hummel.dc.lab1.dto.request.MessageRequestToId
import com.github.hummel.dc.lab1.dto.response.MessageResponseTo
import com.github.hummel.dc.lab1.repository.MessagesRepository
import com.github.hummel.dc.lab1.service.MessageService

class MessageServiceImpl(
	private val repository: MessagesRepository
) : MessageService {
	override suspend fun create(requestTo: MessageRequestTo?): MessageResponseTo? {
		val id = repository.getNextId() ?: return null
		val bean = requestTo?.toBean(id) ?: return null
		val result = repository.create(bean.id, bean) ?: return null

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
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}
}