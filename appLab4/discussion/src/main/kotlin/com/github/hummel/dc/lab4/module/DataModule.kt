package com.github.hummel.dc.lab4.module

import com.datastax.oss.driver.api.core.CqlSession
import com.github.hummel.dc.lab4.dao.MessageDao
import com.github.hummel.dc.lab4.dao.impl.MessageDaoImpl
import com.github.hummel.dc.lab4.repository.MessagesRepository
import com.github.hummel.dc.lab4.repository.impl.MessagesRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val messagesRepositoryQualifier: StringQualifier = StringQualifier("messages_repository")

val dataModule: Module = module {
	single<MessageDao> {
		val cluster: CqlSession = get()

		MessageDaoImpl(cluster)
	}

	single<MessagesRepository>(messagesRepositoryQualifier) {
		val dao = get<MessageDao>()

		MessagesRepositoryImpl(dao)
	}
}