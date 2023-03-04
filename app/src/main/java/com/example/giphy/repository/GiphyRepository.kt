package com.example.giphy.repository

import com.example.giphy.model.GeneralData
import com.example.giphy.retrofit.GiphyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiphyRepository @Inject constructor(private val giphyApi: GiphyApi){

    suspend fun getGifs(keyword: String): Flow<GeneralData?> = flow {
        emit(giphyApi.getGifs(keyword).body())
    }.flowOn(Dispatchers.IO)
}