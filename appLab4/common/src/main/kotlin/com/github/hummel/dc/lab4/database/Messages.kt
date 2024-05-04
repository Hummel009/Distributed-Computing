package com.github.hummel.dc.lab4.database

enum class Messages(private val col: String) {
	TABLE_NAME(
		"tbl_message_by_country"
	),
	COLUMN_ID(
		"id"
	),
	COLUMN_ISSUE_ID(
		"issue_id"
	),
	COLUMN_CONTENT(
		"content"
	),
	COLUMN_COUNTRY(
		"country"
	);

	override fun toString(): String = col

	companion object {
		val SELECT_MESSAGES: String = """
			SELECT
				$COLUMN_COUNTRY,
				$COLUMN_ISSUE_ID,
				$COLUMN_ID,
				$COLUMN_CONTENT
			FROM $TABLE_NAME;
			""".trimIndent()
	}
}