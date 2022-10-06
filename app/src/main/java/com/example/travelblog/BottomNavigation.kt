package com.example.travelblog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: Int) {
    object Home : Screen("home", R.string.home, R.drawable.ic_home)
    object Map : Screen("map", R.string.map, R.drawable.ic_map)
    object Reserv : Screen("reserv", R.string.reserv, R.drawable.ic_reserv)
    object Chat : Screen("chat", R.string.chat, R.drawable.ic_chat)
    object Profile : Screen("profile", R.string.profile, R.drawable.ic_profile)
}

@Composable
fun BottomNavigation() {
    val navController = rememberNavController()

    val item = listOf(
        Screen.Home,
        Screen.Map,
        Screen.Reserv,
        Screen.Chat,
        Screen.Profile,
    )
    Scaffold(bottomBar = {
        BottomNavigation (backgroundColor = colorResource(id = R.color.white)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            item.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = screen.icon),
                            contentDescription = screen.resourceId.toString()
                        )
                    },
                    label = { Text(stringResource(id = screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeFragment()
            }
            composable(Screen.Map.route) { Screen.Chat }
            composable(Screen.Reserv.route) { Screen.Reserv }
            composable(Screen.Chat.route) { Screen.Chat }
            composable(Screen.Profile.route) { Screen.Profile }
        }

    }
}

