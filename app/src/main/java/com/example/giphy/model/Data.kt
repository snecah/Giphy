package com.example.giphy.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    val bitly_gif_url: String,
    val bitly_url: String,
    val embed_url: String,
    val id: String,
    val images: Images,
    @SerializedName("import_datetime")
    val importDatetime: String,
    val is_sticker: Int,
    val rating: String,
    val slug: String,
    val source: String,
    val source_post_url: String,
    val source_tld: String,
    val title: String?,
    val trending_datetime: String,
    val type: String,
    val url: String,
    val user: User,
    val username: String): Serializable