package com.github.hummel.dc.lab2.repository

import com.github.hummel.dc.lab2.bean.Issue

interface IssuesRepository {
	suspend fun create(item: Issue): Long?

	suspend fun getById(id: Long): Issue?

	suspend fun getAll(): List<Issue?>

	suspend fun update(item: Issue): Boolean

	suspend fun deleteById(id: Long): Boolean
}