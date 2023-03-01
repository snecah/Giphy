package com.example.giphy.model

data class GeneralData(
    val `data`: List<Data>,
    val meta: Meta,
    val pagination: Pagination
)