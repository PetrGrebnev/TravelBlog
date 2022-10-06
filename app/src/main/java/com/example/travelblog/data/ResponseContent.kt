package com.example.travelblog.data

data class ResponseContent(
    val success: Boolean,
    val error: ErrorApi,
    val time: String,
    val data: List<Content>,
)
