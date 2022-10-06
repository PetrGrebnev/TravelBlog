package com.example.travelblog.network

import com.example.travelblog.data.ResponseMain
import com.example.travelblog.data.ResponseContent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TravelNetworkService {

    @GET("main?id=117")
    suspend fun getHomeScreen(): Response<ResponseMain>

    @GET("fun?id=117")
    suspend fun getContentFood(@Query("type") url: String): Response<ResponseContent>

    @GET("{rooms}?id=117")
    suspend fun getContentRooms(@Path("rooms") url: String): Response<ResponseContent>
}