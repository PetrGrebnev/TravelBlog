package com.example.travelblog.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelblog.data.ButtonsMain
import com.example.travelblog.data.ContentMain
import com.example.travelblog.data.Content
import com.example.travelblog.repository.TravelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(
    private val repository: TravelRepository
) : ViewModel() {

    var getSuccess: Boolean? by mutableStateOf(false)
    var listButton: List<ButtonsMain>? by mutableStateOf(listOf())
    var listContentMain: List<ContentMain>? by mutableStateOf(listOf())
    var listContentFood: List<Content>? by mutableStateOf(listOf())
    var listContentRooms: List<Content>? by mutableStateOf(listOf())

    init {
        getDataHome()
    }

    private fun getDataHome() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDataHomeScreen()
            listContentMain = data?.data?.content
            listButton = data?.data?.buttons
        }
    }

    fun getContent(url: String, title: String) {
        viewModelScope.launch {
            when(title) {
                "Питание" -> viewModelScope.launch(Dispatchers.IO) {
                    val data = repository.getContentFood(url)
                    getSuccess = data?.success
                    listContentFood = data?.data
                    Log.d("VM", "${data?.data}")
                }.join()
                "Домики и номера" -> viewModelScope.launch(Dispatchers.IO) {
                    val data = repository.getContentFood(url)
                    getSuccess = data?.success
                    listContentRooms = data?.data
                    Log.d("VM", "${data?.data}")
                }.join()
            }

        }
    }
}