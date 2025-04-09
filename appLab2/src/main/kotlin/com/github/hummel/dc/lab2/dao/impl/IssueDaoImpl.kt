package com.github.hummel.dc.lab2.dao.impl

import com.github.hummel.dc.lab2.bean.Issue
import com.github.hummel.dc.lab2.dao.IssueDao
import com.github.hummel.dc.lab2.database.Issues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement
import java.sql.Timestamp

class IssueDaoImpl(private val connection: Connection) : IssueDao {
	init {
		val statement = connection.createStatement()
		statement.executeUpdate(Issues.CREATE_TABLE_ISSUES)
	}

	private fun ResultSet.getString(value: Issues): String = getString("$value")
	private fun ResultSet.getLong(value: Issues): Long = getLong("$value")
	private fun ResultSet.getTimestamp(value: Issues): Timestamp = getTimestamp("$value")

	override suspend fun create(item: Issue): Long = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Issues.INSERT_ISSUE, Statement.RETURN_GENERATED_KEYS)
		statement.apply {
			setLong(1, item.authorId)
			setString(2, item.title)
			setString(3, item.content)
			setTimestamp(4, item.created)
			setTimestamp(5, item.modified)
			executeUpdate()
		}

		val generatedKeys = statement.generatedKeys
		if (generatedKeys.next()) {
			generatedKeys.getLong(1)
		} else {
			throw Exception("Unable to retrieve the id of the newly inserted item.")
		}
	}

	override suspend fun deleteById(id: Long): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Issues.DELETE_ISSUE)
		statement.setLong(1, id)

		try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

	override suspend fun getAll(): List<Issue?> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Issue>()
		val statement = connection.prepareStatement(Issues.SELECT_ISSUES)

		val resultSet = statement.executeQuery()
		while (resultSet.next()) {
			val id = resultSet.getLong(Issues.COLUMN_ID)
			val authorId = resultSet.getLong(Issues.COLUMN_AUTHOR_ID)
			val title = resultSet.getString(Issues.COLUMN_TITLE)
			val content = resultSet.getString(Issues.COLUMN_CONTENT)
			val created = resultSet.getTimestamp(Issues.COLUMN_CREATED)
			val modified = resultSet.getTimestamp(Issues.COLUMN_MODIFIED)
			result.add(Issue(id, authorId, title, content, created, modified))
		}

		result
	}

	override suspend fun getById(id: Long): Issue = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Issues.SELECT_ISSUE_BY_ID)
		statement.setLong(1, id)

		val resultSet = statement.executeQuery()
		if (resultSet.next()) {
			val authorId = resultSet.getLong(Issues.COLUMN_AUTHOR_ID)
			val title = resultSet.getString(Issues.COLUMN_TITLE)
			val content = resultSet.getString(Issues.COLUMN_CONTENT)
			val created = resultSet.getTimestamp(Issues.COLUMN_CREATED)
			val modified = resultSet.getTimestamp(Issues.COLUMN_MODIFIED)
			Issue(id, authorId, title, content, created, modified)
		} else {
			throw Exception("Item record not found.")
		}
	}

	override suspend fun update(item: Issue): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Issues.UPDATE_ISSUE)
		statement.apply {
			setLong(1, item.authorId)
			setString(2, item.title)
			setString(3, item.content)
			setTimestamp(4, item.created)
			setTimestamp(5, item.modified)
			item.id?.let { setLong(6, it) }
		}

		try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not modify item record.")
		}
	}
}