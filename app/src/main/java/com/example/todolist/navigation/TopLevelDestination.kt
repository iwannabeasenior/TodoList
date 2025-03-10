package com.example.todolist.navigation

import androidx.annotation.StringRes
import com.example.todolist.R


sealed class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    @StringRes val titleTextId: Int,
    val route: String,
) {
    data object Today : TopLevelDestination(
        selectedIcon = R.drawable.ic_today_selected,
        unselectedIcon = R.drawable.ic_today,
        titleTextId = R.string.title_today,
        route = "today"
    )
    data object Calendar : TopLevelDestination(
        selectedIcon = R.drawable.ic_calendar_selected,
        unselectedIcon = R.drawable.ic_calendar,
        titleTextId = R.string.title_calendar,
        route = "calendar"
    )
    data object Search : TopLevelDestination(
        selectedIcon = R.drawable.ic_search_selected,
        unselectedIcon = R.drawable.ic_search,
        titleTextId = R.string.title_search,
        route = "search"
    )
    data object Profile : TopLevelDestination(
        selectedIcon = R.drawable.ic_menu_selected,
        unselectedIcon = R.drawable.ic_menu,
        titleTextId = R.string.title_menu,
        route = "profile"
    )
    companion object {
        fun getEntries() = listOf<TopLevelDestination>(
            Today,
            Calendar,
            Search,
            Profile
        )
        // initialize val entries => error unfinished init
    }
}
