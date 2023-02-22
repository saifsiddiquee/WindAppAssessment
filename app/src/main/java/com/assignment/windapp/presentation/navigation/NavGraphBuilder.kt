package com.assignment.windapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.assignment.windapp.presentation.screens.auth.AuthScreen
import com.assignment.windapp.presentation.screens.fund_transfer.FundTransferScreen

@Composable
fun NavGraphSetUp(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppNavigation.AuthScreen.route
    ) {
        composable(route = AppNavigation.AuthScreen.route) {
            AuthScreen(navHostController)
        }
        composable(route = AppNavigation.FundTransferScreen.route) {
            FundTransferScreen(navHostController)
        }
    }
}