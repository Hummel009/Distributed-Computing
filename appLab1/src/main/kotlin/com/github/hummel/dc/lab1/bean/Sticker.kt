package com.github.hummel.dc.lab1.bean

import com.github.hummel.dc.lab1.dto.response.StickerResponseTo
import kotlinx.serialization.Serializable

@Serializable
data class Sticker(
	val id: Long, val name: String
) {
	fun toResponse(): StickerResponseTo = StickerResponseTo(id, name)
}