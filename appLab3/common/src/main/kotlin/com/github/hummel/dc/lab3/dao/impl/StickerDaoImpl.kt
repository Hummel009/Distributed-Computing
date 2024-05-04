package com.github.hummel.dc.lab3.dao.impl

import com.github.hummel.dc.lab3.bean.Sticker
import com.github.hummel.dc.lab3.dao.StickerDao
import com.github.hummel.dc.lab3.database.Stickers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Statement

class StickerDaoImpl(private val connection: Connection) : StickerDao {
	init {
		val statement = connection.createStatement()
		statement.executeUpdate(Stickers.CREATE_TABLE_STICKERS)
	}

	private fun ResultSet.getString(value: Stickers): String = getString("$value")
	private fun ResultSet.getLong(value: Stickers): Long = getLong("$value")

	override suspend fun create(item: Sticker): Long = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Stickers.INSERT_STICKER, Statement.RETURN_GENERATED_KEYS)
		statement.apply {
			setString(1, item.name)
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
		val statement = connection.prepareStatement(Stickers.DELETE_STICKER)
		statement.setLong(1, id)

		return@withContext try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not delete item record.")
		}
	}

	override suspend fun getAll(): List<Sticker> = withContext(Dispatchers.IO) {
		val result = mutableListOf<Sticker>()
		val statement = connection.prepareStatement(Stickers.SELECT_STICKERS)

		val resultSet = statement.executeQuery()
		while (resultSet.next()) {
			val id = resultSet.getLong(Stickers.COLUMN_ID)
			val name = resultSet.getString(Stickers.COLUMN_NAME)
			result.add(Sticker(id, name))
		}

		result
	}

	override suspend fun getById(id: Long): Sticker = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Stickers.SELECT_STICKER_BY_ID)
		statement.setLong(1, id)

		val resultSet = statement.executeQuery()
		if (resultSet.next()) {
			val name = resultSet.getString(Stickers.COLUMN_NAME)
			return@withContext Sticker(id, name)
		} else {
			throw Exception("Item record not found.")
		}
	}

	override suspend fun update(item: Sticker): Int = withContext(Dispatchers.IO) {
		val statement = connection.prepareStatement(Stickers.UPDATE_STICKER)
		statement.apply {
			setString(1, item.name)
			item.id?.let { setLong(2, it) }
		}

		return@withContext try {
			statement.executeUpdate()
		} catch (_: Exception) {
			throw Exception("Can not modify item record.")
		}
	}
}