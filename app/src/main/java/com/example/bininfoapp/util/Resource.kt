package com.example.bininfoapp.util

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    /**
     * Состояние успешной загрузки данных.
     * @param data Загруженные данные.
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Состояние ошибки.
     * @param message Сообщение об ошибке.
     * @param data Возможно, какие-то данные, которые удалось загрузить.
     */
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)

    /**
     * Состояние загрузки.
     * @param data Возможно, старые данные, которые можно показывать во время загрузки новых.
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)
}