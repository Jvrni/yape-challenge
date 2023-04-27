package com.service.search.usecase

import com.service.helpers.or
import com.service.search.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRecipe(private val recipeRepository: RecipeRepository) {

    suspend fun execute() = withContext(Dispatchers.IO) {
        try {
            val list = recipeRepository.getRecipes().safeResponse() or { emptyList() }
            list.map { it.map() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun RecipeResponse.map() = Recipe(
        id = this.id,
        name = this.name,
        rating = this.rating,
        image = this.image,
        timeMin = this.timeMin,
        location = this.location.map(),
        ingredients = this.ingredients.map { it.map() },
        details = this.details.map { it.map() }
    )

    private fun LocationResponse.map() =
        Location(
            lat = this.lat,
            long = this.long,
        )

    private fun IngredientResponse.map() =
        Ingredient(
            name = this.name,
            description = this.description,
            image = this.image,
        )

    private fun DetailResponse.map() = Detail(description = this.description)
}