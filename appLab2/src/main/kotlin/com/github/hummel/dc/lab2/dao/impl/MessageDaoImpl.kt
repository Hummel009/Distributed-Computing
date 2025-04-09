package com.github.hummel.dc.lab2.dao.impl

import com.github.hummel.dc.lab2.bean.Message
import com.github.hummel.dc.lab2.dao.MessageDao
import com.github.hummel.dc.lab2.database.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class MessageDaoImpl(private val connection: Connection) : MessageDao {
	init {
		val statement = connection.createStatement()
		statement.executeUpdate(Messages.CREATE_TABLE_MESSAGES)
	}

	private fun ResultSet.getString(value: Messages): String = getString("$value")
	private fun ResultSet.getLong(value: Messages): Long = getLong("$value")

	override suspend fun create(item: Message): Long = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Messages.INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS)
		statement.apply {
			setLong(1, item.issueId)
			setString(2, item.content)
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
		val statement = connection.prepareStatement(Messages.DELETE_MESSAGE)
		statement.setLong(1, id)

		try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

	override suspend fun getAll(): List<Message> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Message>()
		val statement = connection.prepareStatement(Messages.SELECT_MESSAGES)

		val resultSet = statement.executeQuery()
		while (resultSet.next()) {
			val id = resultSet.getLong(Messages.COLUMN_ID)
			val issueId = resultSet.getLong(Messages.COLUMN_ISSUE_ID)
			val content = resultSet.getString(Messages.COLUMN_CONTENT)
			result.add(Message(id, issueId, content))
		}

		result
	}

	override suspend fun getById(id: Long): Message = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Messages.SELECT_MESSAGE_BY_ID)
		statement.setLong(1, id)

		val resultSet = statement.executeQuery()
		if (resultSet.next()) {
			val issueId = resultSet.getLong(Messages.COLUMN_ISSUE_ID)
			val content = resultSet.getString(Messages.COLUMN_CONTENT)
			Message(id, issueId, content)
		} else {
			throw Exception("Item record not found.")
		}
	}

	override suspend fun update(item: Message): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Messages.UPDATE_MESSAGE)
		statement.apply {
			setLong(1, item.issueId)
			setString(2, item.content)
			item.id?.let { setLong(3, it) }
		}

		try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not modify item record.")
		}
	}
}