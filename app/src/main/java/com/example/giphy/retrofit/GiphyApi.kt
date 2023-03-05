package com.example.giphy.retrofit

import com.example.giphy.Constants
import com.example.giphy.model.GeneralData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("v1/gifs/search?api_key=${Constants.api_key}")
    suspend fun getGifs(
        @Query("q") keyword: String,
        @Query("limit") limit: Int = Constants.DEFAULT_LIMIT
    ): GeneralData
}