package dev.estebanbarrios.airshare.data.entities.utils


sealed class ModelResponse<T>

class EmptyResponse<T> : ModelResponse<T>()

data class SuccessResponse<T>(
    val body: T
) : ModelResponse<T>()

data class ErrorResponse<T>(val errorCode: Int = -1, val errorMessage: String) : ModelResponse<T>()

data class Loading<T>(val boolean: Boolean) : ModelResponse<T>()