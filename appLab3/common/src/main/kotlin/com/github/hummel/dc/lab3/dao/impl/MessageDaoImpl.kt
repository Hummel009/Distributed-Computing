package com.github.hummel.dc.lab3.dao.impl

import com.github.hummel.dc.lab3.bean.Message
import com.github.hummel.dc.lab3.dao.MessageDao
import com.github.hummel.dc.lab3.database.Messages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageDaoImpl(private val session: com.datastax.driver.core.Session) : MessageDao {
	init {
		session.execute(
			"""
			CREATE TABLE IF NOT EXISTS ${Messages.TABLE_NAME}
			(
				${Messages.COLUMN_COUNTRY} text,
				${Messages.COLUMN_ISSUE_ID} bigint,
				${Messages.COLUMN_ID} bigint,
				${Messages.COLUMN_CONTENT} text,
				PRIMARY KEY
				(
					(
						${Messages.COLUMN_COUNTRY}
					), 
					${Messages.COLUMN_ISSUE_ID}, 
					${Messages.COLUMN_ID}
				)
			)
			""".trimIndent()
		)
	}

	override suspend fun create(item: Message): Long = withContext(Dispatchers.IO) {
		session.execute(
			"""
			INSERT
			INTO ${Messages.TABLE_NAME}
			(
				${Messages.COLUMN_COUNTRY},
				${Messages.COLUMN_ISSUE_ID},
				${Messages.COLUMN_ID},
				${Messages.COLUMN_CONTENT}
			)
			VALUES
			(
				'${item.country}',
				${item.issueId}, 
				${item.id}, 
				'${item.content}'
			);
			""".trimIndent()
		)

		val rs = session.execute(Messages.SELECT_MESSAGES)
		val generatedKeys = rs.all()
		if (generatedKeys.isNotEmpty()) {
			return@withContext generatedKeys.maxByOrNull {
				it.getLong(Messages.COLUMN_ID.toString())
			}?.getLong(Messages.COLUMN_ID.toString()) ?: 1
		} else {
			throw Exception("Unable to retrieve the id of the newly inserted item.")
		}
	}

	override suspend fun deleteById(id: Long): Int = withContext(Dispatchers.IO) {
		return@withContext try {
			val entity = getById(id)
			session.execute(
				"""
				DELETE
				FROM ${Messages.TABLE_NAME}
				WHERE ${Messages.COLUMN_ID} = $id
					AND ${Messages.COLUMN_COUNTRY} = '${entity.country}'
					AND ${Messages.COLUMN_ISSUE_ID} = ${entity.issueId};
				""".trimIndent()
			)

			1
		} catch (_: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

	override suspend fun getAll(): List<Message> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Message>()

		val resultSet = session.execute(Messages.SELECT_MESSAGES)
		for (row in resultSet.all()) {
			val id = row.getLong(Messages.COLUMN_ID.toString())
			val issueId = row.getLong(Messages.COLUMN_ISSUE_ID.toString())
			val content = row.getString(Messages.COLUMN_CONTENT.toString())
			val country = row.getString(Messages.COLUMN_COUNTRY.toString())
			result.add(
				Message(
					id = id, issueId = issueId, content = content, country = country
				)
			)
		}

		result
	}

	override suspend fun getById(id: Long): Message = withContext(Dispatchers.IO) {
		val all = getAll()
		if (all.isNotEmpty()) {
			return@withContext all.last { it.id == id }
		} else {
			throw Exception("Item record not found.")
		}
	}

	override suspend fun update(item: Message): Int = withContext(Dispatchers.IO) {
		return@withContext try {
			session.execute(
				"""
				UPDATE ${Messages.TABLE_NAME}
				SET ${Messages.COLUMN_CONTENT} = '${item.content}' 
				WHERE ${Messages.COLUMN_ID} = ${item.id}
					AND ${Messages.COLUMN_COUNTRY} = '${item.country}'
					AND ${Messages.COLUMN_ISSUE_ID} = ${item.issueId};
				""".trimMargin()
			)

			1
		} catch (_: Exception) {
			throw Exception("Can not modify item record.")
		}
	}
}