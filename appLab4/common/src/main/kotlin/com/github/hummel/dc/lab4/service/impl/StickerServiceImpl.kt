package com.github.hummel.dc.lab4.service.impl

import com.github.hummel.dc.lab4.dto.request.StickerRequestTo
import com.github.hummel.dc.lab4.dto.request.StickerRequestToId
import com.github.hummel.dc.lab4.dto.response.StickerResponseTo
import com.github.hummel.dc.lab4.repository.StickersRepository
import com.github.hummel.dc.lab4.service.StickerService

class StickerServiceImpl(
	private val repository: StickersRepository
) : StickerService {
	override suspend fun create(requestTo: StickerRequestTo?): StickerResponseTo? {
		val bean = requestTo?.toBean(null) ?: return null
		val id = repository.create(bean) ?: return null
		val result = bean.copy(id = id)

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

		if (!repository.update(bean)) {
			throw Exception("Exception during item updating!")
		}

		val result = bean.copy()

		return result.toResponse()
	}
}