package com.service.search.repository

import com.service.helpers.Result
import com.service.helpers.Service

class RecipeRepository (
    private val service: Service,
    private val recipeService: RecipeService
) {

    fun getRecipes(): Result<List<RecipeResponse>> =
        service.executeSafe(recipeService.getRecipes())
}
