package com.service.search.usecase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.service.helpers.or
import com.service.search.repository.Repositories
import com.service.search.repository.Repository
import com.service.search.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchSource(
    private val searchRepository: SearchRepository,
    private val language: String
) : PagingSource<Int, Repository>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        return withContext(Dispatchers.IO) {
            try {
                val result =
                    searchRepository.getRepositories(language, params.key ?: STARTING_PAGE_INDEX)
                        .safeResponse() or { Repositories(emptyList()) }

                LoadResult.Page(
                    data = result.items,
                    prevKey = null,
                    nextKey = if (result.items.isNotEmpty()) params.key?.plus(1)
                        ?: STARTING_PAGE_INDEX.plus(1) else null
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}