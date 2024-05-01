package com.example.nln

import android.content.Context
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nln.AddEditIncExp.AddEditDetailView
import com.example.nln.Homeview.HomeView
import com.example.nln.IncExpView.IncExpView
import com.example.nln.ProfileView.ProfileView
import com.example.nln.ViewModels.AuthTokenViewModel
import com.example.nln.ViewModels.ExpenseRecorViewModel
import com.example.nln.Stats.StatsView

@Composable
fun MainView(
    authTokenViewModel: AuthTokenViewModel,
    token: String,
    context: Context
) {
    val navController = rememberNavController()
    val expenseRecorViewModel = ExpenseRecorViewModel(token)
    Scaffold(
        Modifier
            .background(colorResource(id = R.color.background))
            .animateContentSize(),
//        contentWindowInsets = WindowInsets.statusBars,
        bottomBar = {
            BottomNavigation(
                Modifier
                    .background(Color.White),
                backgroundColor = Color.White
            ) {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                screensInDraw.forEach { screen ->
                    val iconTint =
                        if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) {
                            colorResource(id = R.color.boderimage)
                        } else {
                            Color.Black
                        }
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = "",
                                tint = iconTint,
                                modifier = Modifier.height(32.dp)
                            )
                        },
//                        label = { Text(stringResource(screen.dTitle.toInt())) },
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
        }

    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.DrawScreen.Home.route,
            Modifier
                .padding(innerPadding),
        ) {
            composable(Screen.DrawScreen.Home.route) {
                HomeView(
                    navController,
                    authTokenViewModel,
                    expenseRecorViewModel,
                    context
                )
            }
            composable(Screen.DrawScreen.Stats.route) { StatsView(expenseRecorViewModel,context) }
            composable(Screen.DrawScreen.IncExp.route) { IncExpView(authTokenViewModel,expenseRecorViewModel) }
            composable(Screen.DrawScreen.Profile.route) { ProfileView(authTokenViewModel,context) }
            composable(
                Screen.DrawScreen.AddEditInc.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        defaultValue = "0"
                        nullable = false
                    })
            ) {

                val id = if(it.arguments != null) it.arguments!!.getString("id") else "empty"
                if (id != null) {
                    Log.d("aaaaaaaaaaaaaaaaaa",id)
                }
                if (id != null) {
                    AddEditDetailView(
                        navController,
                        authTokenViewModel,
                        expenseRecorViewModel,
                        true,
                        id= id,
                    )
                }
            }
            composable(
                Screen.DrawScreen.AddEditExp.route + "/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        defaultValue = "0"
                        nullable = false
                    })
            ) {
                val id = if(it.arguments != null) it.arguments!!.getString("id") else "empty"
                if (id != null) {
                    AddEditDetailView(
                        navController,
                        authTokenViewModel,
                        expenseRecorViewModel,
                        false,
                        id = id,
                    )
                }
            }
        }

    }
}