package com.github.hummel.dc.lab3.module

import com.datastax.driver.core.Cluster
import com.github.hummel.dc.lab3.dao.MessageDao
import com.github.hummel.dc.lab3.dao.impl.MessageDaoImpl
import com.github.hummel.dc.lab3.repository.MessagesRepository
import com.github.hummel.dc.lab3.repository.impl.MessagesRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val messagesRepositoryQualifier: StringQualifier = StringQualifier("messages_repository")

val dataModule: Module = module {
	single<MessageDao> {
		val cluster: Cluster = get()

		MessageDaoImpl(cluster.connect("distcomp"))
	}

	single<MessagesRepository>(messagesRepositoryQualifier) {
		val dao = get<MessageDao>()

		MessagesRepositoryImpl(dao)
	}
}