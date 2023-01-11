package com.service.helpers

@JvmInline
value class Result<T>(private val value: Any?) {

    companion object {
        fun <T> ok(value: T): Result<T> = Result(value)
        fun <T> error(exception: Exception): Result<T> = Result(Failure(exception))
        fun <T> error(error: String): Result<T> = Result(Failure(Exception(error)))
    }

    @Suppress("UNCHECKED_CAST")
    fun getOrThrow(): T = when (value) {
        is Failure -> throw value.exception
        else -> value as T
    }

    open class Failure(val exception: Exception)

    fun safeResponse(): ContinuationBuilder<T> = ContinuationBuilder { getOrThrow() }
}

@JvmInline
value class ContinuationBuilder<T>(val run: suspend () -> T)

suspend infix fun <T> ContinuationBuilder<T>.or(defaultValue: (value : Exception) -> T): T = try {
    this.run()
} catch (e: Exception) { defaultValue(e) }