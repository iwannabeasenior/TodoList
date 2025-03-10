package com.example.todolist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.todolist.navigation.TopLevelDestination
import com.example.todolist.navigation.TopLevelNavHost

@Composable
fun TodoApp(modifier: Modifier = Modifier) {
//    var itemSelected by remember {
//        mutableStateOf(TopLevelDestination.Today.route)
//    }
    val navHostController = rememberNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: TopLevelDestination.Today.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBarContent(
                itemSelected = currentRoute,
                itemClick = { },
                navHostController = navHostController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                shape = CircleShape,
                containerColor = Color.Red,
            ) {
                Icon(
                    Icons.Default.Add,
                    "",
                    tint = Color.White,
                )
            }
        },
        topBar = {

        }
    ) { innerPadding ->
        Box(
           modifier = Modifier.padding(innerPadding).padding(15.dp)
        ) {
            TopLevelNavHost(navHostController = navHostController)
        }
    }

}

@Composable
fun BottomAppBarContent(itemSelected: String, itemClick: (route: String) -> Unit, navHostController: NavHostController) {

    BottomAppBar {
        TopLevelDestination.getEntries().forEach { item ->
            NavigationBarItem(
                selected = itemSelected == item.route,
                onClick = {
                    if (itemSelected != item.route) {
                        navHostController.navigate(item.route) {
                            popUpTo(navHostController.graph.startDestinationId) {
                                // save state for case going back
                                saveState = true

                                /*  false -> no pop matching ID, pop all immediate
                                    true -> pop matching ID but immediate keep */
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (itemSelected == item.route) item.selectedIcon
                                else item.unselectedIcon
                        ),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = { Text(stringResource(item.titleTextId)) }
            )
        }
    }
}
