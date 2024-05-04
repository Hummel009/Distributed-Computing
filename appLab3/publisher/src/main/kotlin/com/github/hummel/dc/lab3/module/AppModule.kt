package com.github.hummel.dc.lab3.module

import com.github.hummel.dc.lab3.repository.AuthorsRepository
import com.github.hummel.dc.lab3.repository.IssuesRepository
import com.github.hummel.dc.lab3.repository.StickersRepository
import com.github.hummel.dc.lab3.service.AuthorService
import com.github.hummel.dc.lab3.service.IssueService
import com.github.hummel.dc.lab3.service.StickerService
import com.github.hummel.dc.lab3.service.impl.AuthorServiceImpl
import com.github.hummel.dc.lab3.service.impl.IssueServiceImpl
import com.github.hummel.dc.lab3.service.impl.StickerServiceImpl
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
	single<StickerService> {
		val repository: StickersRepository = get(stickersRepositoryQualifier)

		StickerServiceImpl(repository)
	}
}