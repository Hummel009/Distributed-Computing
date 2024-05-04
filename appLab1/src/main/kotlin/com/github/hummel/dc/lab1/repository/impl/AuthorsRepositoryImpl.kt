package com.github.hummel.dc.lab1.repository.impl

import com.github.hummel.dc.lab1.bean.Author
import com.github.hummel.dc.lab1.repository.AuthorsRepository

class AuthorsRepositoryImpl : AuthorsRepository {
	override val data: MutableList<Pair<Long, Author>> = mutableListOf()

	override suspend fun create(id: Long, item: Author): Author? {
		val flag = data.add(id to item)
		return if (flag) item else null
	}

	override suspend fun deleteById(id: Long): Boolean = data.removeIf { it.first == id }

	override suspend fun getAll(): List<Author?> = data.map { it.second }

	override suspend fun getById(id: Long): Author? = data.find { it.first == id }?.second

	override suspend fun getNextId(): Long? {
		return if (data.isEmpty()) {
			-1
		} else {
			var maxKey = 0L

			data.forEach { maxKey = maxOf(it.first, maxKey) }

			data.find { it.first == maxKey }?.second?.id ?: return null
		} + 1
	}
}