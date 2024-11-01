package me.looma.ui

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data class Loaded<out T>(val data: T) : DataState<T>()
    data object Error : DataState<Nothing>()
}