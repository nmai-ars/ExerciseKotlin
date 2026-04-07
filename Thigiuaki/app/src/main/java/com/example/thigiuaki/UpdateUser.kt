package com.example.thigiuaki

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class UpdateUser : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id") ?: ""
        val usernameOld = intent.getStringExtra("username") ?: ""
        val passwordOld = intent.getStringExtra("password") ?: ""
        val roleOld = intent.getStringExtra("role") ?: ""

        setContent {
            com.example.thigiuaki.ui.theme.ThigiuakiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UpdateScreen(id, usernameOld, passwordOld, roleOld)
                }
            }
        }
    }

    @Composable
    fun UpdateScreen(id: String, u: String, p: String, r: String) {

        val context = LocalContext.current
        val db = FirebaseFirestore.getInstance()

        var username by remember { mutableStateOf(u) }
        var password by remember { mutableStateOf(p) }
        var role by remember { mutableStateOf(r) }

        Column(modifier = Modifier.padding(16.dp)) {

            Text("CẬP NHẬT USER", style = MaterialTheme.typography.titleLarge)

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
                label = { Text("Role") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isBlank() || password.isBlank() || role.isBlank()) {
                        Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val updateData = hashMapOf(
                        "username" to username,
                        "password" to password,
                        "role" to role
                    )

                    db.collection("users")
                        .document(id)
                        .set(updateData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                            finish() // quay lại màn trước
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cập nhật")
            }
        }
    }
}