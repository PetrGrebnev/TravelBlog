package com.example.travelblog.data

data class ResponseMain(
    val success: Boolean,
    val error: ErrorApi,
    val time: String,
    val data: DataMain
)
