package com.example.travelblog.data

data class Content(
    val id: Int,
    val date: Date,
    val url: String,
    val image: ImageApi,
    val title: String,
    val subtitle: String,
    val content: String
)
