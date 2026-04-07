package com.example.midterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.midterm.ui.theme.MidtermTheme
import com.google.firebase.auth.FirebaseAuth



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MidtermTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
                ){

                    Mynavigation()

                }
            }
        }
    }
}

@Composable
fun Mynavigation()
{
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Signin.rout
    ){
        composable(Screen.Signin.rout){
            SignIn(navController = navController)
        }
        composable( Screen.Home.rout){
            HomeScreen(navController = navController)
        }
        composable(Screen.Signup.rout){
            SignUp(navController = navController)
        }
    }
}