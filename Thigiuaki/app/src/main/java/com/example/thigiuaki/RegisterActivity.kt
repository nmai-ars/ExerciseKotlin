package com.example.thigiuaki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : ComponentActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()

        setContent {
            com.example.thigiuaki.ui.theme.ThigiuakiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RegisterScreen()
                }
            }
        }
    }

    @Composable
    fun RegisterScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var role by remember { mutableStateOf("") }

        val context = this@RegisterActivity

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Text(
                text = "ĐĂNG KÝ",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = role,
                onValueChange = { role = it },
                label = { Text("Role (admin hoặc user)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank() || role.isBlank()) {
                        Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    db.collection("users")
                        .whereEqualTo("username", username)
                        .get()
                        .addOnSuccessListener { result ->
                            if (!result.isEmpty) {
                                Toast.makeText(context, "Username đã tồn tại", Toast.LENGTH_SHORT).show()
                            } else {
                                val user = hashMapOf(
                                    "username" to username,
                                    "password" to password,
                                    "role" to role
                                )

                                db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Lỗi đăng ký", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Lỗi kiểm tra username", Toast.LENGTH_SHORT).show()
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng ký")
            }

            Spacer(modifier = Modifier.height(8.dp)) // Tạo một khoảng trống nhỏ

            OutlinedButton(
                onClick = {
                    // Lệnh finish() sẽ đóng trang Đăng ký hiện tại
                    // và tự động quay về trang LoginActivity trước đó
                    finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đã có tài khoản? Quay lại đăng nhập")
            }
        }
    }
}