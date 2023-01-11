package com.service.search.repository

import com.service.helpers.Result
import com.service.helpers.Service

class SearchRepository (
    private val service: Service,
    private val searchService: SearchService
) {

    fun getRepositories(language: String, page: Int): Result<Repositories> =
        service.executeSafe(searchService.getRepositories(language, PER_PAGE, page))

    companion object {
        private const val PER_PAGE = 20
    }
}
