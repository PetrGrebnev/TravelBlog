package com.example.travelblog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelblog.data.ButtonsMain
import com.example.travelblog.data.Content
import com.example.travelblog.viewmodel.TravelViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun HomeFragment(
    viewModel: TravelViewModel = hiltViewModel()
) {

    val listButtons = viewModel.listButton ?: listOf()

    val listContentMain = viewModel.listContentMain ?: listOf()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                text = "Главная",
                style = MaterialTheme.typography.body1,
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            ) {
                items(listButtons) { item ->
                    SetButtonList(item)
                }
            }
        }
        for (it in listContentMain.indices) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = listContentMain[it].title,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )
            }
            val listContentFood = viewModel.listContentFood ?: listOf()
            val listContentRooms = viewModel.listContentRooms ?: listOf()
            when (listContentMain[it].title) {
                "Питание" -> {
                    viewModel.getContent(listContentMain[it].url, listContentMain[it].title)
                    val successContent = viewModel.getSuccess ?: false
                    if (successContent) {
                        items(listContentFood) { item ->
                            if (listContentFood.indexOf(item) < 6) {
                                SetFoodList(food = item)
                            }
                        }
                    }
                }
                "Домики и номера" -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        val successContent = viewModel.getSuccess ?: false
                        viewModel.getContent(listContentMain[it].url, listContentMain[it].title)
                        if (successContent) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(listContentRooms) { item ->
                                    if (listContentRooms.indexOf(item) < 6) {
                                        SetHouseList(house = item)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetButtonList(btn: ButtonsMain) {
    Surface(
        shape = MaterialTheme.shapes.small,
        elevation = 2.dp,
        color = colorResource(
            id = when (btn.color) {
                "g-13" -> R.color.g_13
                "g-11" -> R.color.g_11
                "g-12" -> R.color.g_12
                "g-19" -> R.color.g_19
                else -> R.color.white
            }
        )
    ) {
        Row {
            Icon(
                painter = painterResource(
                    when (btn.icon) {
                        "rst-weather-night" -> R.drawable.rst_weather_cloudy
                        "rst-help" -> R.drawable.rst_help
                        "rst-play-circle" -> R.drawable.rst_play_circle
                        "rst-map_marker_path" -> R.drawable.rst_map_marker_path
                        else -> R.drawable.ic_chat
                    }
                ),
                modifier = Modifier.padding(20.dp),
                contentDescription = null,
                tint = colorResource(id = R.color.white)
            )
            Text(
                color = colorResource(id = R.color.white),
                text = btn.title,
                modifier = Modifier.padding(top = 22.dp, end = 22.dp)
            )
        }
    }
}

@Composable
fun SetFoodList(food: Content) {
    Card(
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Card(
                shape = MaterialTheme.shapes.small
            ) {
                GlideImage(
                    imageModel = food.image.md,
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }
            Text(
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight(600),
                fontSize = 20.sp,
                text = food.title,
                color = colorResource(id = R.color.black)
            )
            Text(
                modifier = Modifier.padding(2.dp),
                text = food.subtitle,
                color = colorResource(id = R.color.black),
                maxLines = 2,
                fontWeight = FontWeight(400),
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun SetHouseList(house: Content) {
    Card(
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(2.dp)
        ) {
            Card(
                shape = MaterialTheme.shapes.small
            ) {
                GlideImage(
                    imageModel = house.image.md,
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )
            }
            Text(
                modifier = Modifier.padding(2.dp),
                fontWeight = FontWeight(600),
                fontSize = 20.sp,
                text = house.title,
                color = colorResource(id = R.color.black)
            )
//            Text(
//                modifier = Modifier.padding(2.dp),
//                text = house.subtitle,
//                color = colorResource(id = R.color.black),
//                maxLines = 2,
//                fontWeight = FontWeight(400),
//                fontSize = 16.sp,
//            )
        }
    }
}

