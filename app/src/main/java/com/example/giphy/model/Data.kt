package com.example.giphy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    val id: String,
    val images: Images,
    @SerializedName("import_datetime")
    val importDatetime: String,
    val rating: String,
    val title: String?
) : Serializable