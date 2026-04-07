package com.example.midterm

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavHostController){
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") } // Đã thêm biến này

    // Cập nhật FocusRequester cho 3 trường dữ liệu
    val (focusUsername, focusPassword, focusConfirmPassword) = remember {FocusRequester.createRefs()}
    val keyboardController = LocalSoftwareKeyboardController.current

    var isPasswordVisible by remember{ mutableStateOf(false) }
    var isConfirmPasswordVisible by remember{ mutableStateOf(false) } // Toggle cho Confirm Password

    // Khởi tạo các thành phần cần thiết để dùng Toast và Firebase
    val context = LocalContext.current
    var firebaseAuth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCF4)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.40f)
        ){
            Image(
                painter = painterResource(id = R.drawable.signup),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds)
        }
        Spacer(modifier = Modifier.size(15.dp))
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Wellcome To ", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(text = " PIZZERIA!", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB90020),)
            }
            Spacer(modifier = Modifier.size(28.dp))
            OutlinedTextField(value = username, onValueChange = {username = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
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
                .height(65.dp)
                .focusRequester(focusPassword),
                value = password,
                onValueChange = {password = it},
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = {focusConfirmPassword.requestFocus()}), // Nhấn Next chuyển sang ô Confirm
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
            Spacer(modifier = Modifier.size(9.dp))

            // Đã đổi biến lưu state thành confirmPassword
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .focusRequester(focusConfirmPassword),
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {keyboardController?.hide()}),
                visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {isConfirmPasswordVisible = !isConfirmPasswordVisible}) {
                        Icon(imageVector = if(isConfirmPasswordVisible)Icons.Default.LockOpen else Icons.Default.Lock,
                            contentDescription = "Cf Password Toggle",
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
                label = { Text(text = "Confirm Password", color = Color(0xFFB90020),)},

                )
            Spacer(modifier = Modifier.size(20.dp))

            // Xử lý tạo tài khoản Firebase
            Button(onClick = {
                if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.Signin.rout)
                            } else {
                                Toast.makeText(context, task.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier.fillMaxWidth().height(50.dp),
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
                Text(text = "Sign Up", fontWeight = FontWeight.Bold,fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center,verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Already have an account?",textAlign = TextAlign.Center)
                TextButton(onClick = {
                    navController.navigate(Screen.Signin.rout)
                },
                ) {
                    Text(text = "Sign In now!",fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB90020))
                }
            }
            Spacer(modifier = Modifier.size(6.dp))
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.Center) {
                Text(text = "OR Sign Up with")
            }
            Spacer(modifier = Modifier.size(20.dp))
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