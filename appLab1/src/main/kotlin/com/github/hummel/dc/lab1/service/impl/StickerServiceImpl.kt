package com.github.hummel.dc.lab1.service.impl

import com.github.hummel.dc.lab1.dto.request.StickerRequestTo
import com.github.hummel.dc.lab1.dto.request.StickerRequestToId
import com.github.hummel.dc.lab1.dto.response.StickerResponseTo
import com.github.hummel.dc.lab1.repository.StickersRepository
import com.github.hummel.dc.lab1.service.StickerService

class StickerServiceImpl(
	private val repository: StickersRepository
) : StickerService {
	override suspend fun create(requestTo: StickerRequestTo?): StickerResponseTo? {
		val id = repository.getNextId() ?: return null
		val bean = requestTo?.toBean(id) ?: return null
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}

	override suspend fun deleteById(id: Long): Boolean = repository.deleteById(id)

	override suspend fun getAll(): List<StickerResponseTo> {
		val result = repository.getAll()

		return result.filterNotNull().map { it.toResponse() }
	}

	override suspend fun getById(id: Long): StickerResponseTo? {
		val result = repository.getById(id) ?: return null

		return result.toResponse()
	}

	override suspend fun update(requestTo: StickerRequestToId?): StickerResponseTo? {
		val bean = requestTo?.toBean() ?: return null
		val result = repository.create(bean.id, bean) ?: return null

		return result.toResponse()
	}
}