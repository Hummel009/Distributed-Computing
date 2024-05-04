package com.github.hummel.dc.lab2.dao

import com.github.hummel.dc.lab2.bean.Sticker

interface StickerDao {
	suspend fun create(item: Sticker): Long

	suspend fun deleteById(id: Long): Int

	suspend fun getAll(): List<Sticker?>

	suspend fun getById(id: Long): Sticker

	suspend fun update(item: Sticker): Int
}