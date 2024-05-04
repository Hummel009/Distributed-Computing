package com.github.hummel.dc.lab3.repository.impl

import com.github.hummel.dc.lab3.bean.Message
import com.github.hummel.dc.lab3.dao.MessageDao
import com.github.hummel.dc.lab3.repository.MessagesRepository

class MessagesRepositoryImpl(
	private val dao: MessageDao
) : MessagesRepository {
	override suspend fun create(item: Message): Long? {
		return try {
			dao.create(item)
		} catch (_: Exception) {
			null
		}
	}

	override suspend fun deleteById(id: Long): Boolean = dao.deleteById(id) > 0

	override suspend fun getAll(): List<Message?> = dao.getAll()

	override suspend fun getById(id: Long): Message? {
		return try {
			dao.getById(id)
		} catch (_: Exception) {
			null
		}
	}

	override suspend fun update(item: Message): Boolean = dao.update(item) > 0
}