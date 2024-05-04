package com.github.hummel.dc.lab5.service

import com.github.hummel.dc.lab5.dto.request.StickerRequestTo
import com.github.hummel.dc.lab5.dto.request.StickerRequestToId
import com.github.hummel.dc.lab5.dto.response.StickerResponseTo

interface StickerService {
	suspend fun create(requestTo: StickerRequestTo?): StickerResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<StickerResponseTo>

	suspend fun getById(id: Long): StickerResponseTo?

	suspend fun update(requestTo: StickerRequestToId?): StickerResponseTo?
}