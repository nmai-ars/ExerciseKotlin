package com.example.firebaseproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
// THÊM DÒNG NÀY VÀO
import androidx.compose.material3.Card
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.firebaseproject.ui.theme.FireBaseProjectTheme
import com.example.firebaseproject.ui.theme.FireBaseProjectTheme

import com.google.firebase.firestore.FirebaseFirestore

class CourseDetailsActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireBaseProjectTheme()   {
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
                            val courseList = remember { mutableStateListOf<Course?>() }
                            val db: FirebaseFirestore = FirebaseFirestore.getInstance()

                            db.collection("Courses").get()
                                .addOnSuccessListener { queryDocumentSnapshots ->
                                    if (!queryDocumentSnapshots.isEmpty) {
                                        val list = queryDocumentSnapshots.documents
                                        for (d in list) {
                                            val c: Course? = d.toObject(Course::class.java)
                                            c?.courseID = d.id
                                            courseList.add(c)
                                        }
                                    } else {
                                        Toast.makeText(this@CourseDetailsActivity, "No data found", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this@CourseDetailsActivity, "Fail to get the data.", Toast.LENGTH_SHORT).show()
                                }

                            firebaseUI(LocalContext.current, courseList)
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun firebaseUI(context: Context, courseList: List<Course?>) {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth().background(Color.White),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn {
                itemsIndexed(courseList) { index, item ->
                    Card(
                        onClick = {
                            val i = Intent(context, UpdateCourse::class.java)
                            i.putExtra("Name", item?.courseName)
                            i.putExtra("courseDuration", item?.courseDuration)
                            i.putExtra("courseDescription", item?.courseDescription)
                            i.putExtra("courseID", item?.courseID)
                            context.startActivity(i)
                        },
                        modifier = Modifier.padding(8.dp),
                        // Đổi dòng cũ thành dòng này:
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.width(5.dp))
                            courseList[index]?.courseName?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(4.dp),
                                    color =  Color(0xFF0F9D58),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            courseList[index]?.courseDuration?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(4.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = 15.sp)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            courseList[index]?.courseDescription?.let {
                                Text(
                                    text = it,
                                    modifier = Modifier.padding(4.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(fontSize = 15.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}