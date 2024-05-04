package com.github.hummel.dc.lab5

import io.lettuce.core.RedisClient
import io.lettuce.core.api.sync.RedisCommands

var id: Int = 0

private lateinit var operator: RedisCommands<String, String>

fun main() {
	configureRedis()
	for (i in 0..9) {
		println("Key: $i, Value: ${operator.get("$i")}")
	}
}

fun configureRedis() {
	val redisClient = RedisClient.create("redis://localhost")
	val connection = redisClient.connect()
	operator = connection.sync()

	testViaRedis("From Redis", "Connection established!")
}

fun testViaRedis(key: String, value: String) {
	try {
		operator.set(key, value)
		val test = operator.get(key)
		println("Key: $key, Value: $test")
	} catch (_: Exception) {
	}
}