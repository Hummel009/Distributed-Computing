package com.github.hummel.dc.lab4.database

enum class Authors(private val col: String) {
	TABLE_NAME(
		"tbl_author"
	),
	COLUMN_ID(
		"id"
	),
	COLUMN_LOGIN(
		"login"
	),
	COLUMN_PASSWORD(
		"password"
	),
	COLUMN_FIRSTNAME(
		"firstname"
	),
	COLUMN_LASTNAME(
		"lastname"
	);

	override fun toString(): String = col

	companion object {
		val CREATE_TABLE_AUTHORS: String = """
			CREATE TABLE IF NOT EXISTS $TABLE_NAME (
				$COLUMN_ID SERIAL PRIMARY KEY, 
				$COLUMN_LOGIN VARCHAR(64) UNIQUE, 
				$COLUMN_PASSWORD VARCHAR(128), 
				$COLUMN_FIRSTNAME VARCHAR(64), 
				$COLUMN_LASTNAME VARCHAR(64)
			);
			""".trimIndent()

		val INSERT_AUTHOR: String = """
			INSERT
			INTO $TABLE_NAME (
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME
			) VALUES (?, ?, ?, ?);
			""".trimIndent()

		val SELECT_AUTHOR_BY_ID: String = """
			SELECT
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME 
			FROM $TABLE_NAME 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val SELECT_AUTHORS: String = """
			SELECT 
				$COLUMN_ID, 
				$COLUMN_LOGIN, 
				$COLUMN_PASSWORD, 
				$COLUMN_FIRSTNAME, 
				$COLUMN_LASTNAME
			FROM $TABLE_NAME;
			""".trimIndent()

		val UPDATE_AUTHOR: String = """
			UPDATE $TABLE_NAME 
			SET 
				$COLUMN_LOGIN = ?, 
				$COLUMN_PASSWORD = ?, 
				$COLUMN_FIRSTNAME = ?, 
				$COLUMN_LASTNAME = ? 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val DELETE_AUTHOR: String = """
			DELETE
			FROM $TABLE_NAME 
			WHERE $COLUMN_ID = ?;
			""".trimIndent()
	}
}