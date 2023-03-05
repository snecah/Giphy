package com.example.giphy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Images(
    @SerializedName("downsized_large")
    val downsizedLarge: DownsizedLarge,
    val original: Original,
) : Serializable