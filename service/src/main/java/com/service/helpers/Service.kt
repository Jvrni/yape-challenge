package com.service.helpers

import retrofit2.Call

object Service {

    fun <T> executeSafe(call: Call<T>): Result<T> {
        try {
            val response = call.execute()
            response.let { safeResponse ->
                val body = safeResponse.body()
                if (safeResponse.isSuccessful.not() || body == null) {
                    var errorBodyString = "{}"
                    safeResponse.errorBody()?.let {
                        val errorBody = it.string()
                        if (errorBody.isBlank().not()) {
                            errorBodyString = errorBody
                        }
                    }
                    return Result.error(errorBodyString)
                }
                return Result.ok(body)
            }
        } catch (e: Exception) {
            return Result.error(e)
        }
    }
}