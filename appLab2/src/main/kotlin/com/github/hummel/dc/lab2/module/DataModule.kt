package com.github.hummel.dc.lab2.module

import com.github.hummel.dc.lab2.dao.AuthorDao
import com.github.hummel.dc.lab2.dao.IssueDao
import com.github.hummel.dc.lab2.dao.MessageDao
import com.github.hummel.dc.lab2.dao.StickerDao
import com.github.hummel.dc.lab2.dao.impl.AuthorDaoImpl
import com.github.hummel.dc.lab2.dao.impl.IssueDaoImpl
import com.github.hummel.dc.lab2.dao.impl.MessageDaoImpl
import com.github.hummel.dc.lab2.dao.impl.StickerDaoImpl
import com.github.hummel.dc.lab2.repository.AuthorsRepository
import com.github.hummel.dc.lab2.repository.IssuesRepository
import com.github.hummel.dc.lab2.repository.MessagesRepository
import com.github.hummel.dc.lab2.repository.StickersRepository
import com.github.hummel.dc.lab2.repository.impl.AuthorsRepositoryImpl
import com.github.hummel.dc.lab2.repository.impl.IssuesRepositoryImpl
import com.github.hummel.dc.lab2.repository.impl.MessagesRepositoryImpl
import com.github.hummel.dc.lab2.repository.impl.StickersRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import java.sql.Connection

val authorsRepositoryQualifier: StringQualifier = StringQualifier("authors_repository")
val issuesRepositoryQualifier: StringQualifier = StringQualifier("issues_repository")
val messagesRepositoryQualifier: StringQualifier = StringQualifier("messages_repository")
val stickersRepositoryQualifier: StringQualifier = StringQualifier("stickers_repository")

val dataModule: Module = module {
	single<AuthorDao> {
		val dbConnection = get<Connection>()

		AuthorDaoImpl(dbConnection)
	}
	single<IssueDao> {
		val dbConnection = get<Connection>()

		IssueDaoImpl(dbConnection)
	}
	single<MessageDao> {
		val dbConnection = get<Connection>()

		MessageDaoImpl(dbConnection)
	}
	single<StickerDao> {
		val dbConnection = get<Connection>()

		StickerDaoImpl(dbConnection)
	}

	single<AuthorsRepository>(authorsRepositoryQualifier) {
		val dao = get<AuthorDao>()

		AuthorsRepositoryImpl(dao)
	}
	single<IssuesRepository>(issuesRepositoryQualifier) {
		val dao = get<IssueDao>()

		IssuesRepositoryImpl(dao)
	}
	single<MessagesRepository>(messagesRepositoryQualifier) {
		val dao = get<MessageDao>()

		MessagesRepositoryImpl(dao)
	}
	single<StickersRepository>(stickersRepositoryQualifier) {
		val dao = get<StickerDao>()

		StickersRepositoryImpl(dao)
	}
}