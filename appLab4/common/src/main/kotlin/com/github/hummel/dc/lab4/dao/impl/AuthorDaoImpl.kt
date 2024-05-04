package com.github.hummel.dc.lab4.dao.impl

import com.github.hummel.dc.lab4.bean.Author
import com.github.hummel.dc.lab4.dao.AuthorDao
import com.github.hummel.dc.lab4.database.Authors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class AuthorDaoImpl(private val connection: Connection) : AuthorDao {
	init {
		val statement = connection.createStatement()
		statement.executeUpdate(Authors.CREATE_TABLE_AUTHORS)
	}

	private fun ResultSet.getString(value: Authors): String = getString("$value")
	private fun ResultSet.getLong(value: Authors): Long = getLong("$value")

	override suspend fun create(item: Author): Long = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Authors.INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)
		statement.apply {
			setString(1, item.login)
			setString(2, item.password)
			setString(3, item.firstname)
			setString(4, item.lastname)
			executeUpdate()
		}

		val generatedKeys = statement.generatedKeys
		if (generatedKeys.next()) {
			return@withContext generatedKeys.getLong(1)
		} else {
			throw Exception("Unable to retrieve the id of the newly inserted item.")
		}
	}

	override suspend fun deleteById(id: Long): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Authors.DELETE_AUTHOR)
		statement.setLong(1, id)

		return@withContext try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

	override suspend fun getAll(): List<Author?> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Author>()
		val statement = connection.prepareStatement(Authors.SELECT_AUTHORS)

		val resultSet = statement.executeQuery()
		while (resultSet.next()) {
			val id = resultSet.getLong(Authors.COLUMN_ID)
			val login = resultSet.getString(Authors.COLUMN_LOGIN)
			val password = resultSet.getString(Authors.COLUMN_PASSWORD)
			val firstname = resultSet.getString(Authors.COLUMN_FIRSTNAME)
			val lastname = resultSet.getString(Authors.COLUMN_LASTNAME)
			result.add(
				Author(
					id = id, login = login, password = password, firstname = firstname, lastname = lastname
				)
			)
		}

		result
	}

	override suspend fun getById(id: Long): Author = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Authors.SELECT_AUTHOR_BY_ID)
		statement.setLong(1, id)

		val resultSet = statement.executeQuery()
		if (resultSet.next()) {
			val login = resultSet.getString(Authors.COLUMN_LOGIN)
			val password = resultSet.getString(Authors.COLUMN_PASSWORD)
			val firstname = resultSet.getString(Authors.COLUMN_FIRSTNAME)
			val lastname = resultSet.getString(Authors.COLUMN_LASTNAME)
			return@withContext Author(
				id = id, login = login, password = password, firstname = firstname, lastname = lastname
			)
		} else {
			throw Exception("Item record not found.")
		}
	}

	override suspend fun update(item: Author): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Authors.UPDATE_AUTHOR)
		statement.apply {
			setString(1, item.login)
			setString(2, item.password)
			setString(3, item.firstname)
			setString(4, item.lastname)
			item.id?.let { setLong(5, it) }
		}

		return@withContext try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not modify item record.")
		}
	}
}