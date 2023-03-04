package com.example.giphy.repository

import com.example.giphy.model.GeneralData
import com.example.giphy.retrofit.ApiErrorException
import com.example.giphy.retrofit.GiphyApi
import com.example.giphy.retrofit.NetworkFailureException
import com.example.giphy.retrofit.NetworkResponse
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
    }

//    suspend fun getGifs(keyword: String): Flow<GeneralData> = flow {
//        when(val dataFromNetwork = giphyApi.getGifs(keyword)) {
//            is NetworkResponse.Success -> { emit(dataFromNetwork.value) }
//            is NetworkResponse.ApiError -> {throw ApiErrorException(dataFromNetwork.error, dataFromNetwork.code) }
//            is NetworkResponse.Failure -> { throw dataFromNetwork.error ?: NetworkFailureException()
//            }
//        }
//    }.flowOn(Dispatchers.IO)

//    suspend fun getGifs(keyword: String) = giphyApi.getGifs(keyword)

}