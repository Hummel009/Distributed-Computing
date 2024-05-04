package com.github.hummel.dc.lab2.module

import com.github.hummel.dc.lab2.repository.AuthorsRepository
import com.github.hummel.dc.lab2.repository.IssuesRepository
import com.github.hummel.dc.lab2.repository.MessagesRepository
import com.github.hummel.dc.lab2.repository.StickersRepository
import com.github.hummel.dc.lab2.service.AuthorService
import com.github.hummel.dc.lab2.service.IssueService
import com.github.hummel.dc.lab2.service.MessageService
import com.github.hummel.dc.lab2.service.StickerService
import com.github.hummel.dc.lab2.service.impl.AuthorServiceImpl
import com.github.hummel.dc.lab2.service.impl.IssueServiceImpl
import com.github.hummel.dc.lab2.service.impl.MessageServiceImpl
import com.github.hummel.dc.lab2.service.impl.StickerServiceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
	single<AuthorService> {
		val repository: AuthorsRepository = get(authorsRepositoryQualifier)

		AuthorServiceImpl(repository)
	}
	single<IssueService> {
		val repository: IssuesRepository = get(issuesRepositoryQualifier)

		IssueServiceImpl(repository)
	}
	single<MessageService> {
		val repository: MessagesRepository = get(messagesRepositoryQualifier)

		MessageServiceImpl(repository)
	}
	single<StickerService> {
		val repository: StickersRepository = get(stickersRepositoryQualifier)

		StickerServiceImpl(repository)
	}
}