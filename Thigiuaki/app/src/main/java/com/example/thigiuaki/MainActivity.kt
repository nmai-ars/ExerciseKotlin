package com.example.thigiuaki

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            com.example.thigiuaki.ui.theme.ThigiuakiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserScreen() {
        val context = LocalContext.current
        val db = FirebaseFirestore.getInstance()

        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var role by remember { mutableStateOf("") }

        val userList = remember { mutableStateListOf<Pair<String, User>>() }

        fun loadUsers() {
            db.collection("users")
                .get()
                .addOnSuccessListener { result ->
                    userList.clear()
                    for (doc in result) {
                        val user = doc.toObject(User::class.java)
                        userList.add(Pair(doc.id, user))
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show()
                }
        }

        LaunchedEffect(Unit) {
            loadUsers()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "QUẢN LÝ USER",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF0F9D58)
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(12.dp)
                    .fillMaxSize()
            ) {
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

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (username.isBlank() || password.isBlank() || role.isBlank()) {
                            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val user = hashMapOf(
                            "username" to username,
                            "password" to password,
                            "role" to role
                        )

                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Thêm user thành công", Toast.LENGTH_SHORT).show()
                                username = ""
                                password = ""
                                role = ""
                                loadUsers()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Thêm user thất bại", Toast.LENGTH_SHORT).show()
                            }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Thêm user")
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(userList) { index, item ->
                        val user = item.second

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {

                                Text(
                                    text = "Username: ${user.username}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F9D58)
                                )

                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Password: ${user.password}")
                                Text("Role: ${user.role}")

                                Spacer(modifier = Modifier.height(10.dp))

                                Row {
                                    Button(onClick = {
                                        val intent = Intent(context, UpdateUser::class.java)
                                        intent.putExtra("id", item.first)
                                        intent.putExtra("username", user.username)
                                        intent.putExtra("password", user.password)
                                        intent.putExtra("role", user.role)
                                        context.startActivity(intent)
                                    }) {
                                        Text("Sửa")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Button(onClick = {
                                        db.collection("users")
                                            .document(item.first)
                                            .delete()
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                                                userList.removeAt(index)
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                                            }
                                    }) {
                                        Text("Xóa")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}