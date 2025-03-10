package com.example.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolist.screen.today.TodayScreen

@Composable
fun TopLevelNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = TopLevelDestination.Today.route
    ) {
        composable(TopLevelDestination.Today.route) {
            TodayScreen()
        }
        composable(TopLevelDestination.Calendar.route) {


        }
        composable(TopLevelDestination.Search.route) {

        }

        composable(TopLevelDestination.Profile.route) {

        }
    }
}