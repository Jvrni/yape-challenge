package com.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.service.search.repository.OwnerRaw
import com.service.search.repository.Repositories
import com.service.search.repository.Repository
import com.service.search.usecase.GetRepositories
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    @MockK
    private lateinit var getRepositories: GetRepositories

    private val viewModel by lazy {
        spyk(SearchViewModel(getRepositories = getRepositories))
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = false)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `GIVEN a fake information WHEN getting it THEN return success value`() {
        // Given
        val item = Repositories(
            listOf(
                Repository(
                    name = "Java",
                    description = null,
                    owner = OwnerRaw("Image Teste"),
                    createDate = "2022/09/11",
                    updateDate = "2022/09/12"
                ),
                Repository(
                    name = "Kotlin",
                    description = "It is wonderful",
                    owner = OwnerRaw("Image Teste"),
                    createDate = "2022/09/11",
                    updateDate = "2022/09/12"
                )
            )
        )

        // When
        coEvery {
            getRepositories.execute(any())
        } returns flowOf(PagingData.from(item.items))

        viewModel.search("Kotlin")

        //Then
        verify(exactly = 1) {
            viewModel.search("Kotlin")
        }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is SearchState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assertEquals(
                            listOf(item).first().items[0].name,
                            result.data.take(1).first().collectDataForTest()[0].name
                        )

                        assertNull(result.data.take(1).first().collectDataForTest()[0].description)
                        assertNotNull(
                            result.data.take(1).first().collectDataForTest()[1].description
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `GIVEN a fake information WHEN getting it THEN return error`() {
        // Given
        val item = Repositories(
            listOf(
                Repository(
                    name = "Java",
                    description = null,
                    owner = OwnerRaw("Image Teste"),
                    createDate = "2022/09/11",
                    updateDate = "2022/09/12"
                ),
                Repository(
                    name = "Kotlin",
                    description = "It is wonderful",
                    owner = OwnerRaw("Image Teste"),
                    createDate = "2022/09/11",
                    updateDate = "2022/09/12"
                )
            )
        )

        // When
        coEvery {
            getRepositories.execute(any())
        } returns flowOf(PagingData.empty())

        viewModel.search("Kotlin")

        //Then
        verify(exactly = 1) {
            viewModel.search("Kotlin")
        }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is SearchState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assertTrue(result.data.take(1).first().collectDataForTest().isEmpty())
                        assertTrue(item.items.size > result.data.take(1).first().collectDataForTest().size)
                    }
                }
            }
        }
    }
}

private suspend fun <T : Any> PagingData<T>.collectDataForTest(): List<T> {
    val dcb = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
    val items = mutableListOf<T>()
    val dif = object : PagingDataDiffer<T>(dcb, TestCoroutineDispatcher()) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ): Int? {
            for (idx in 0 until newList.size)
                items.add(newList.getFromStorage(idx))
            onListPresentable()
            return null
        }
    }
    dif.collectFrom(this)
    return items
}
