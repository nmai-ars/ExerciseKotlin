package com.example.midterm

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(navController: NavHostController){
    val offset = Offset(5.0f, 6.0f)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color(0xFFF1C137)),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Row {
            Text(
                text = "PIZZERIA",
                style = TextStyle(
                    fontSize = 38.sp,
                    color = Color(0xFFD32C2C),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 9.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        offset = offset,
                        blurRadius = 0.5f
                    )
                )
            )
        }
        Spacer(modifier = Modifier.size(30.dp))
        Row {
            Text(
                text = "Delivering \n" +
                        "Deliciousness right\n" +
                        "to your door!",
                fontSize = 32.sp,
                letterSpacing = 1.sp,
                lineHeight = 35.sp,
                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        }
//        Spacer(modifier = Modifier.size(20.dp))
        Image(
            painter = painterResource(id = R.drawable._cham),
            contentDescription = "3doc",
            modifier = Modifier
                .size(60.dp)
        )

//        Spacer(modifier = Modifier.size(20.dp))
        Button(onClick = {
            // da hang
        },
//            modifier = Modifier.height(45.dp),
            shape = RoundedCornerShape(35.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 15.dp,
                pressedElevation = 6.dp
            ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB90020)
            ),
            border = BorderStroke(0.5.dp, Color.Red)
        ) {
            Text(text = "START ORDER",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.size(58.dp))
        Button(onClick = {
            var firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            navController.navigate(Screen.Signin.rout)
        },
//            modifier = Modifier.height(45.dp),
            shape = RoundedCornerShape(35.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 15.dp,
                pressedElevation = 6.dp
            ),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB90020)
            ),
            border = BorderStroke(0.5.dp, Color.Red)
        ) {
            Text(text = "SignOut",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        Image(
            painter = painterResource(id = R.drawable.man),
            contentDescription = "man",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

    }
}