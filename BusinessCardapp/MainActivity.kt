package com.example.business

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.LocationOn


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BusinessCardApp()
        }
    }
}

@Composable
fun BusinessCardApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFDDEFE6))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ProfileSection()
        }
        ContactSection()
    }
}


@Composable
fun ProfileSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Image(
            painter = painterResource(id = R.drawable.my_photo),
            contentDescription = "My Photo",
            modifier = Modifier
                .size(170.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFF00796B), CircleShape)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ngọc Mai ITer",
            fontSize = 34.sp,
            fontWeight = FontWeight.Light
        )

        Text(
            text = "Happy Birthday 🎉",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00796B)
        )
    }
}

@Composable
fun ContactSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ContactRow(Icons.Filled.Phone, "+84 392 840 394")
        ContactRow(Icons.Filled.LocationOn, "Da Nang, Viet Nam")
        ContactRow(Icons.Filled.Email, "maitran122333a@gmail.com")
    }
}

@Composable
fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    info: String
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF00796B),
            modifier = Modifier.padding(end = 16.dp)
        )

        Text(
            text = info,
            fontSize = 16.sp
        )
    }
}
