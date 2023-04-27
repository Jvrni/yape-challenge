package com.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.service.search.repository.*
import com.service.search.usecase.GetRecipe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    @MockK
    private lateinit var getRecipe: GetRecipe

    private val viewModel by lazy {
        spyk(HomeViewModel(getRecipe = getRecipe))
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = false)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `GIVEN a fake information WHEN getting it THEN return success value`() {
        // Given
        val list = listOf(
            Recipe(
                id = 0,
                name = "Pizza",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            ),
            Recipe(
                id = 1,
                name = "Cake",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            )
        )

        // When
        coEvery { getRecipe.execute() } returns list
        viewModel.recipes()

        //Then
        verify(exactly = 1) { viewModel.recipes() }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is RecipesState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assertEquals(
                            list.first().name,
                            result.data.first().name
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `GIVEN a fake information WHEN getting it with rating filter THEN return success value`() {
        // Given
        val list = listOf(
            Recipe(
                id = 0,
                name = "Pizza",
                rating = 3,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            ),
            Recipe(
                id = 1,
                name = "Cake",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            )
        )

        // When
        coEvery { getRecipe.execute() } returns list
        viewModel.recipes(filter = FilterOption.Rating)

        //Then
        verify(exactly = 1) { viewModel.recipes(filter = FilterOption.Rating) }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is RecipesState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assertEquals(
                            list.maxByOrNull { it.rating }!!.name,
                            result.data.first().name
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `GIVEN a fake information WHEN getting it with time filter THEN return success value`() {
        // Given
        val list = listOf(
            Recipe(
                id = 0,
                name = "Pizza",
                rating = 3,
                image = "",
                timeMin = 40,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            ),
            Recipe(
                id = 1,
                name = "Cake",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            )
        )

        // When
        coEvery { getRecipe.execute() } returns list
        viewModel.recipes(filter = FilterOption.Time)

        //Then
        verify(exactly = 1) { viewModel.recipes(filter = FilterOption.Time) }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is RecipesState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assertEquals(
                            list.maxByOrNull { it.timeMin }!!.name,
                            result.data.first().name
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `GIVEN a fake information WHEN getting it with filter THEN return error value`() {
        // Given
        val list = listOf(
            Recipe(
                id = 0,
                name = "Pizza",
                rating = 3,
                image = "",
                timeMin = 40,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            ),
            Recipe(
                id = 1,
                name = "Cake",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            )
        )

        // When
        coEvery { getRecipe.execute() } returns list
        viewModel.recipes(filter = FilterOption.Rating)

        //Then
        verify(exactly = 1) { viewModel.recipes(filter = FilterOption.Rating) }

        viewModel.viewState.observeForever { result ->
            when (result) {
                is RecipesState.Loaded -> {
                    viewModel.viewModelScope.launch {
                        assert(list.first().name != result.data.first().name)
                    }
                }
            }
        }
    }

    @Test
    fun `GIVEN a fake information WHEN getting it THEN return error`() {
        // Given
        val list = listOf(
            Recipe(
                id = 0,
                name = "Pizza",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            ),
            Recipe(
                id = 1,
                name = "Cake",
                rating = 4,
                image = "",
                timeMin = 30,
                location = Location(0.0, 0.0),
                ingredients = listOf(Ingredient(name = "", description = "", image = "")),
                details = listOf(Detail(""))
            )
        )

        // When
        coEvery {
            getRecipe.execute()
        } returns emptyList()

        viewModel.recipes()

        //Then
        verify(exactly = 1) {
            viewModel.recipes()
        }

        viewModel.viewState.observeForever { result -> assert(result is RecipesState.Empty) }
    }
}
