package com.github.hummel.dc.lab1.module

import com.github.hummel.dc.lab1.repository.AuthorsRepository
import com.github.hummel.dc.lab1.repository.IssuesRepository
import com.github.hummel.dc.lab1.repository.MessagesRepository
import com.github.hummel.dc.lab1.repository.StickersRepository
import com.github.hummel.dc.lab1.service.AuthorService
import com.github.hummel.dc.lab1.service.IssueService
import com.github.hummel.dc.lab1.service.MessageService
import com.github.hummel.dc.lab1.service.StickerService
import com.github.hummel.dc.lab1.service.impl.AuthorServiceImpl
import com.github.hummel.dc.lab1.service.impl.IssueServiceImpl
import com.github.hummel.dc.lab1.service.impl.MessageServiceImpl
import com.github.hummel.dc.lab1.service.impl.StickerServiceImpl
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