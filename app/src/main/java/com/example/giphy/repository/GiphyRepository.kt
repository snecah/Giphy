package com.example.giphy.repository

import com.example.giphy.retrofit.GiphyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(private val giphyApi: GiphyApi){

    suspend fun getGifs(keyword: String) = giphyApi.getGifs(keyword)

}