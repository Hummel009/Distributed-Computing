package com.github.hummel.dc.lab5.repository

import com.github.hummel.dc.lab5.bean.Author

interface AuthorsRepository {
	suspend fun create(item: Author): Long?

	suspend fun getById(id: Long): Author?

	suspend fun getAll(): List<Author?>

	suspend fun update(item: Author): Boolean

	suspend fun deleteById(id: Long): Boolean
}