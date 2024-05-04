package com.github.hummel.dc.lab5.module

import com.github.hummel.dc.lab5.repository.AuthorsRepository
import com.github.hummel.dc.lab5.repository.IssuesRepository
import com.github.hummel.dc.lab5.repository.StickersRepository
import com.github.hummel.dc.lab5.service.AuthorService
import com.github.hummel.dc.lab5.service.IssueService
import com.github.hummel.dc.lab5.service.StickerService
import com.github.hummel.dc.lab5.service.impl.AuthorServiceImpl
import com.github.hummel.dc.lab5.service.impl.IssueServiceImpl
import com.github.hummel.dc.lab5.service.impl.StickerServiceImpl
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