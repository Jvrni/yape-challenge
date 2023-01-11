package com.service.search.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.service.search.repository.Repository
import com.service.search.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class GetRepositories(private val searchRepository: SearchRepository) {

    fun execute(language: String): Flow<PagingData<Repository>> = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { SearchSource(searchRepository, language) }
        ).flow


    companion object {
        const val PAGE_SIZE = 20
    }
}