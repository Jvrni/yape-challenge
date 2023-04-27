package com.service.search.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val name: String,
    val rating: Int,
    val image: String,
    val timeMin: Int,
    val location: Location,
    val ingredients: List<Ingredient>,
    val details: List<Detail>
) : Parcelable


@Parcelize
data class Location(
    val lat: Double,
    val long: Double
) : Parcelable


@Parcelize
data class Ingredient(
    val name: String,
    val description: String,
    val image: String
) : Parcelable


@Parcelize
data class Detail(val description: String) : Parcelable