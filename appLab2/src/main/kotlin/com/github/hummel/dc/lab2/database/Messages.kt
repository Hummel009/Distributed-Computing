package com.github.hummel.dc.lab2.database

enum class Messages(private val col: String) {
	TABLE_NAME(
		"tbl_message"
	),
	COLUMN_ID(
		"id"
	),
	COLUMN_ISSUE_ID(
		"issue_id"
	),
	COLUMN_CONTENT(
		"content"
	);

	override fun toString(): String = col

	companion object {
		val CREATE_TABLE_MESSAGES: String = """
			CREATE TABLE IF NOT EXISTS $TABLE_NAME (
				$COLUMN_ID SERIAL PRIMARY KEY,
				$COLUMN_ISSUE_ID BIGINT,
				$COLUMN_CONTENT VARCHAR(2048),
				FOREIGN KEY ($COLUMN_ISSUE_ID) REFERENCES ${Issues.TABLE_NAME} (${Issues.COLUMN_ID}) ON UPDATE NO ACTION
			);
			""".trimIndent()

		val INSERT_MESSAGE: String = """
			INSERT INTO $TABLE_NAME (
				$COLUMN_ISSUE_ID,
				$COLUMN_CONTENT
			) VALUES (?, ?);
			""".trimIndent()

		val SELECT_MESSAGE_BY_ID: String = """
			SELECT
				$COLUMN_ISSUE_ID,
				$COLUMN_CONTENT
			FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val SELECT_MESSAGES: String = """
			SELECT
				$COLUMN_ID,
				$COLUMN_ISSUE_ID,
				$COLUMN_CONTENT
			FROM $TABLE_NAME;
			""".trimIndent()

		val UPDATE_MESSAGE: String = """
			UPDATE $TABLE_NAME
			SET
				$COLUMN_ISSUE_ID = ?,
				$COLUMN_CONTENT = ?
			WHERE $COLUMN_ID = ?;
			""".trimIndent()

		val DELETE_MESSAGE: String = """
			DELETE FROM $TABLE_NAME
			WHERE $COLUMN_ID = ?;
			""".trimIndent()
	}
}