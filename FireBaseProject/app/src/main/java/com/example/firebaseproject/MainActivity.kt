package com.example.firebaseproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseproject.ui.theme.FireBaseProjectTheme

import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireBaseProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0F9D58)),
                                title = {
                                    Text(
                                        text = "GFG",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    ) { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding)) {
                            FirebaseUI(LocalContext.current)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FirebaseUI(context: Context) {
    val courseName = remember { mutableStateOf("") }
    val courseDuration = remember { mutableStateOf("") }
    val courseDescription = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = courseName.value,
            onValueChange = { courseName.value = it },
            placeholder = { Text(text = "Enter your course name") },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDuration.value,
            onValueChange = { courseDuration.value = it },
            placeholder = { Text(text = "Enter your course duration") },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = courseDescription.value,
            onValueChange = { courseDescription.value = it },
            placeholder = { Text(text = "Enter your course description") },
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            textStyle = TextStyle(color = Color.White, fontSize = 15.sp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (TextUtils.isEmpty(courseName.value)) {
                    Toast.makeText(context, "Please enter course name", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(courseDuration.value)) {
                    Toast.makeText(context, "Please enter course Duration", Toast.LENGTH_SHORT).show()
                } else if (TextUtils.isEmpty(courseDescription.value)) {
                    Toast.makeText(context, "Please enter course description", Toast.LENGTH_SHORT).show()
                } else {
                    // Tạo ID ngẫu nhiên cho khoá học mới
                    val generatedCourseID = UUID.randomUUID().toString()
                    addDataToFirebase(generatedCourseID, courseName.value, courseDuration.value, courseDescription.value, context)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(text = "Add Data", modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                context.startActivity(Intent(context, CourseDetailsActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(text = "View Courses", modifier = Modifier.padding(8.dp))
        }
    }
}

fun addDataToFirebase(
    courseID: String, courseName: String, courseDuration: String,
    courseDescription: String, context: Context
) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val courses = Course(courseID, courseName, courseDuration, courseDescription)

    db.collection("Courses").document(courseID).set(courses)
        .addOnSuccessListener {
            Toast.makeText(context, "Your Course has been added to Firebase", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Fail to add course \n$e", Toast.LENGTH_SHORT).show()
        }
}