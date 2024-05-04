package com.github.hummel.dc.lab1.module

import com.github.hummel.dc.lab1.repository.AuthorsRepository
import com.github.hummel.dc.lab1.repository.IssuesRepository
import com.github.hummel.dc.lab1.repository.MessagesRepository
import com.github.hummel.dc.lab1.repository.StickersRepository
import com.github.hummel.dc.lab1.repository.impl.AuthorsRepositoryImpl
import com.github.hummel.dc.lab1.repository.impl.IssuesRepositoryImpl
import com.github.hummel.dc.lab1.repository.impl.MessagesRepositoryImpl
import com.github.hummel.dc.lab1.repository.impl.StickersRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val authorsRepositoryQualifier: StringQualifier = StringQualifier("authors_repository")
val issuesRepositoryQualifier: StringQualifier = StringQualifier("issues_repository")
val messagesRepositoryQualifier: StringQualifier = StringQualifier("messages_repository")
val stickersRepositoryQualifier: StringQualifier = StringQualifier("stickers_repository")

val dataModule: Module = module {
	single<AuthorsRepository>(authorsRepositoryQualifier) {
		AuthorsRepositoryImpl()
	}
	single<IssuesRepository>(issuesRepositoryQualifier) {
		IssuesRepositoryImpl()
	}
	single<MessagesRepository>(messagesRepositoryQualifier) {
		MessagesRepositoryImpl()
	}
	single<StickersRepository>(stickersRepositoryQualifier) {
		StickersRepositoryImpl()
	}
}