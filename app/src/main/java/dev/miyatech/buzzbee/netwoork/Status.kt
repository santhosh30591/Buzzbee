package dev.miyatech.buzzbee.netwoork

sealed class NetworkResult<out T> {
    data class Init(var dd:String) : NetworkResult<Nothing>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}



