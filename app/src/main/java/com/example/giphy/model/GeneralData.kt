package com.example.giphy.model

import com.google.gson.annotations.SerializedName

data class GeneralData(
    @SerializedName("data")
    val gifsData: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)