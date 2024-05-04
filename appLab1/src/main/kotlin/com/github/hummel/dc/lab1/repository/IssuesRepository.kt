package com.github.hummel.dc.lab1.repository

import com.github.hummel.dc.lab1.bean.Issue

interface IssuesRepository {
	val data: MutableList<Pair<Long, Issue>>

	suspend fun create(id: Long, item: Issue): Issue?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<Issue?>

	suspend fun getById(id: Long): Issue?

	suspend fun getNextId(): Long?
}