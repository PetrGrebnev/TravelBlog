package com.example.travelblog.repository

import android.util.Log
import com.example.travelblog.data.ResponseMain
import com.example.travelblog.data.ResponseContent
import com.example.travelblog.network.TravelNetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TravelRepository @Inject constructor(
    private val service: TravelNetworkService
) {

    suspend fun getDataHomeScreen(): ResponseMain?{
        val data = service.getHomeScreen().body()
        Log.d("API", "${data}")
        return data
    }

    suspend fun getContentFood(url: String): ResponseContent? {
        var body: ResponseContent? = null
        var newUrl = url.substringAfterLast('/')
        newUrl = newUrl.substringBeforeLast("?")
        when(newUrl){
            "fun"->{
                newUrl = url.substringAfterLast("=")
                body = service.getContentFood(newUrl).body()
            }
            "rooms"->{
                body = service.getContentRooms(newUrl).body()
            }
            else -> {body == null}
        }
        return body
    }
}