package com.example.bininfoapp.data.remote

import com.example.bininfoapp.data.remote.dto.BinResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BinlistApi {

    /**
     * Запрос для получения информации по BIN.
     * @param bin Номер для поиска.
     */
    @GET("{bin}")
    suspend fun getInfoByBin(
        @Path("bin") bin: String
    ): BinResponseDto

    companion object {
        const val BASE_URL = "https://lookup.binlist.net/"
    }
}