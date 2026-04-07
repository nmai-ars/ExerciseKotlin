package com.example.midterm

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

import android.content.Context

import android.text.TextUtils
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignIn(navController: NavHostController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val (focusUsername, focusPassword) = remember {FocusRequester.createRefs()}
    val keyboardController = LocalSoftwareKeyboardController.current
    var isPasswordVisible by remember{ mutableStateOf(false) }
//    lateinit var firebaseAuth: FirebaseAuth
    val context = LocalContext.current

    var firebaseAuth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCF4)),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.40f)
        ){
            Image(
                painter = painterResource(id = R.drawable.log),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }
        Spacer(modifier = Modifier.size(30.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Wellcome Back ", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = " PIZZERIA!", fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB90020),)
            }
            Spacer(modifier = Modifier.size(30.dp))
            OutlinedTextField(value = username, onValueChange = {username = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 0.dp, bottom = 0.dp)
                    .focusRequester(focusUsername),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {focusPassword.requestFocus()}),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFB90020),
                    unfocusedBorderColor = Color(0xFFB90020),
                ),
                label = { Text(text = "Email", color = Color(0xFFB90020))}
            )
            Spacer(modifier = Modifier.size(9.dp))
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .focusRequester(focusPassword),
                value = password,
                onValueChange = {password = it},
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {isPasswordVisible = !isPasswordVisible}) {
                        Icon(imageVector = if(isPasswordVisible)Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = "Password Toggle",
                            tint = Color(0xFFB90020)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFFB90020),
                    unfocusedBorderColor = Color(0xFFB90020),
                ),
                label = { Text(text = "Password", color = Color(0xFFB90020),)},

                )
            Spacer(modifier = Modifier.size(20.dp))
            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            navController.navigate(Screen.Home.rout)
                        } else {
                            Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(context, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

                }


            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 15.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB90020)
                ),
                border = BorderStroke(0.5.dp, Color.Red)
            ) {
                Text(text = "Sign In", fontWeight = FontWeight.Bold,fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(19.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Is it first for you? ", textAlign = TextAlign.Center)
                TextButton(onClick = {
                    navController.navigate(Screen.Signup.rout)
                },
                ) {
                    Text(text = "Sign Up now!",fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB90020))
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                Text(text = "OR Sign In with")
            }
            Spacer(modifier = Modifier.size(13.dp))
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFFD9D9D9)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gg),
                        contentDescription = "Logo GG",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            border = BorderStroke(0.5.dp, Color(0xFFD9D9D9)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fb),
                        contentDescription = "Logo FB",
                        modifier = Modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                }
            }

        }
    }
}

