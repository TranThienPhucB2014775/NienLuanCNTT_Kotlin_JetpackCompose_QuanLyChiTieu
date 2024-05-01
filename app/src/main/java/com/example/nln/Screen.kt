package com.example.nln

import androidx.annotation.DrawableRes

sealed class Screen(val title: String, val route: String) {
    sealed class DrawScreen(val dTitle: String, val dRoute: String, @DrawableRes val icon: Int) :
        Screen(dTitle, dRoute) {
        object Home : DrawScreen(
            "Home",
            "Home",
            R.drawable.icons8_home
        )

        object Profile : DrawScreen(
            "Profile",
            "Profile",
            R.drawable.baseline_account_box_24
        )

        object Stats : DrawScreen(
            "Stats",
            "Stats",
            R.drawable.baseline_bar_chart_24
        )

        object IncExp : DrawScreen(
            "IncExp",
            "IncExp",
            R.drawable.baseline_assignment_24
        )
        object AddEditInc : DrawScreen(
            "AddEditInc",
            "AddEditInc",
            R.drawable.baseline_assignment_24
        )
        object AddEditExp : DrawScreen(
            "AddEditExp",
            "AddEditExp",
            R.drawable.baseline_assignment_24
        )
        object Login : DrawScreen(
            "Login",
            "Login",
            R.drawable.baseline_assignment_24
        )
        object SignUp : DrawScreen(
            "SignUp",
            "SignUp",
            R.drawable.baseline_assignment_24
        )
        object Intro : DrawScreen(
            "Intro",
            "Intro",
            R.drawable.baseline_assignment_24
        )
        object Main : DrawScreen(
            "Main",
            "Main",
            R.drawable.baseline_assignment_24
        )
        object Auth : DrawScreen(
            "Auth",
            "Auth",
            R.drawable.baseline_assignment_24
        )
        object Splash : DrawScreen(
            "Splash",
            "Splash",
            R.drawable.baseline_assignment_24
        )

    }
}

val screensInDraw = listOf(
    Screen.DrawScreen.Home,
    Screen.DrawScreen.Stats,
    Screen.DrawScreen.IncExp,
    Screen.DrawScreen.Profile
)

//val screensInAuth = listOf(
//    Screen.DrawScreen.Login,
//    Screen.DrawScreen.SignUp,
//    Screen.DrawScreen.Intro
//)