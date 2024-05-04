package com.github.hummel.dc.lab2.repository.impl

import com.github.hummel.dc.lab2.bean.Author
import com.github.hummel.dc.lab2.dao.AuthorDao
import com.github.hummel.dc.lab2.repository.AuthorsRepository

class AuthorsRepositoryImpl(
	private val dao: AuthorDao
) : AuthorsRepository {
	override suspend fun create(item: Author): Long? {
		return try {
			dao.create(item)
		} catch (_: Exception) {
			null
		}
	}

	override suspend fun deleteById(id: Long): Boolean = dao.deleteById(id) > 0

	override suspend fun getAll(): List<Author?> = dao.getAll()

	override suspend fun getById(id: Long): Author? {
		return try {
			dao.getById(id)
		} catch (_: Exception) {
			null
		}
	}

	override suspend fun update(item: Author): Boolean = dao.update(item) > 0
}