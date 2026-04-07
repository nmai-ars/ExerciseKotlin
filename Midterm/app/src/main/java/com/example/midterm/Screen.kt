package com.example.midterm


sealed class Screen(val rout: String){
    object Home : Screen("home")
    object Signin : Screen("signin")
    object Signup : Screen("signup")
}
