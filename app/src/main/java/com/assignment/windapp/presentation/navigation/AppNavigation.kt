package com.assignment.windapp.presentation.navigation

sealed class AppNavigation(val route: String, val title: String) {
    object AuthScreen : AppNavigation(route = "Auth", title = "Auth")
    object FundTransferScreen : AppNavigation(route = "fund-transfer", title = "Fund Transfer")
}