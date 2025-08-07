package com.quantumy.cursofirebaselite

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.quantumy.cursofirebaselite.presentation.home.HomeScreen
import com.quantumy.cursofirebaselite.presentation.initial.InitialScreen
import com.quantumy.cursofirebaselite.presentation.login.LoginScreen
import com.quantumy.cursofirebaselite.presentation.sigup.SigUpScreen

@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    auth: FirebaseAuth
) {
    NavHost(navController = navHostController, startDestination = "initial"){
        composable("initial"){
            InitialScreen(
                navigationToLogin = { navHostController.navigate("login") },
                navigationToSigUp = { navHostController.navigate("sigup") }
            )
        }
        composable("login"){
            LoginScreen(auth,navHostController)
        }
        composable("sigup"){
            SigUpScreen(auth,navHostController)
        }
        composable("home"){
            HomeScreen()
        }

    }
}