package com.example.thigiuaki

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Đổi từ itemsIndexed sang items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

class UserDetailsActivity : ComponentActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = FirebaseFirestore.getInstance()

        setContent {
            com.example.thigiuaki.ui.theme.ThigiuakiTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    UserScreen()
                }
            }
        }
    }

    @Composable
    fun UserScreen() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var role by remember { mutableStateOf("") }

        // 1. TẠO BIẾN TRẠNG THÁI LƯU TRỮ BỘ LỌC HIỆN TẠI ("All", "admin", "user")
        var selectedFilter by remember { mutableStateOf("All") }

        val context = this@UserDetailsActivity
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

        // 2. TẠO DANH SÁCH MỚI ĐÃ ĐƯỢC LỌC TỪ DANH SÁCH GỐC
        val filteredUserList = if (selectedFilter == "All") {
            userList
        } else {
            userList.filter { it.second.role?.lowercase() == selectedFilter.lowercase() }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

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

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank() && role.isNotBlank()) {
                        val user = hashMapOf(
                            "username" to username,
                            "password" to password,
                            "role" to role
                        )

                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()
                                username = ""
                                password = ""
                                role = ""
                                loadUsers()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Lỗi thêm", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm user")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. THÊM HÀNG NÚT BẤM ĐỂ CHỌN BỘ LỌC (Nằm trên danh sách)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { selectedFilter = "All" },
                    colors = ButtonDefaults.buttonColors(
                        // Nếu đang chọn thì hiện màu hồng Theme, không thì màu xám
                        containerColor = if (selectedFilter == "All") MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    Text("Tất cả")
                }

                Button(
                    onClick = { selectedFilter = "admin" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedFilter == "admin") MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.weight(1f).padding(horizontal = 2.dp)
                ) {
                    Text("Admin")
                }

                Button(
                    onClick = { selectedFilter = "user" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedFilter == "user") MaterialTheme.colorScheme.primary else Color.Gray
                    ),
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    Text("User")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 4. HIỂN THỊ DANH SÁCH ĐÃ LỌC (filteredUserList) THAY VÌ DANH SÁCH GỐC
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredUserList) { item ->
                    val docId = item.first
                    val user = item.second

                    UserItem(
                        user = user,
                        onDelete = {
                            db.collection("users")
                                .document(docId)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show()
                                    // Sửa lỗi: Cập nhật lại data từ Firebase thay vì xóa theo index
                                    loadUsers()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Lỗi xóa", Toast.LENGTH_SHORT).show()
                                }
                        },
                        onUpdate = { newUser ->
                            val updateData = hashMapOf(
                                "username" to newUser.username,
                                "password" to newUser.password,
                                "role" to newUser.role
                            )

                            db.collection("users")
                                .document(docId)
                                .set(updateData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Cập nhật OK", Toast.LENGTH_SHORT).show()
                                    loadUsers()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
                                }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(
                onClick = {
                    startActivity(Intent(this@UserDetailsActivity, LoginActivity::class.java))
                    finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Đăng xuất")
            }
        }
    }

    @Composable
    fun UserItem(
        user: User,
        onDelete: () -> Unit,
        onUpdate: (User) -> Unit
    ) {
        var editing by remember { mutableStateOf(false) }

        var username by remember { mutableStateOf(user.username ?: "") }
        var password by remember { mutableStateOf(user.password ?: "") }
        var role by remember { mutableStateOf(user.role ?: "") }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {

                if (editing) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = role,
                        onValueChange = { role = it },
                        label = { Text("Role") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        onUpdate(User(username, password, role))
                        editing = false
                    }) {
                        Text("Cập nhật")
                    }

                } else {
                    Text("Username: ${user.username}")
                    Text("Password: ${user.password}")
                    Text("Role: ${user.role}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Button(onClick = { editing = true }) {
                            Text("Sửa")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(onClick = onDelete) {
                            Text("Xóa")
                        }
                    }
                }
            }
        }
    }
}