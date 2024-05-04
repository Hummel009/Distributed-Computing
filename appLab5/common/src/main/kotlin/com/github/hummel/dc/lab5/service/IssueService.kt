package com.github.hummel.dc.lab5.service

import com.github.hummel.dc.lab5.dto.request.IssueRequestTo
import com.github.hummel.dc.lab5.dto.request.IssueRequestToId
import com.github.hummel.dc.lab5.dto.response.IssueResponseTo

interface IssueService {
	suspend fun create(requestTo: IssueRequestTo?): IssueResponseTo?

	suspend fun deleteById(id: Long): Boolean

	suspend fun getAll(): List<IssueResponseTo>

	suspend fun getById(id: Long): IssueResponseTo?

	suspend fun update(requestTo: IssueRequestToId?): IssueResponseTo?
}