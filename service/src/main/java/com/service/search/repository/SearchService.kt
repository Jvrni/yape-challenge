package com.service.search.repository

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") language: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<Repositories>
}

data class Repositories(
    val items: List<Repository>
)

@Keep
@Parcelize
data class Repository(
    val name: String,
    val description: String?,
    val owner: OwnerRaw,
    @SerializedName("created_at") val createDate: String,
    @SerializedName("updated_at") val updateDate: String,
): Parcelable


@Keep
@Parcelize
data class OwnerRaw(
    @SerializedName("avatar_url") val image: String,
): Parcelable
