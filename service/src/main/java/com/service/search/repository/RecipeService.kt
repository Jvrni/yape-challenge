package com.service.search.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET

interface RecipeService {
    @GET("recipes")
    fun getRecipes(): Call<List<RecipeResponse>>
}

@Parcelize
data class RecipeResponse(
    val id: Int,
    val name: String,
    val rating: Int,
    @SerializedName("image_url") val image: String,
    @SerializedName("time_min") val timeMin: Int,
    val location: LocationResponse,
    val ingredients: List<IngredientResponse>,
    val details: List<DetailResponse>
) : Parcelable


@Parcelize
data class LocationResponse(
    val lat: Double,
    val long: Double
) : Parcelable


@Parcelize
data class IngredientResponse(
    val name: String,
    val description: String,
    @SerializedName("image_url") val image: String
) : Parcelable


@Parcelize
data class DetailResponse(val description: String) : Parcelable
