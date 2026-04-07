package com.example.firebaseproject

data class Course(
    var courseID: String? = "", // Phải là var
    var courseName: String? = "",
    var courseDuration: String? = "",
    var courseDescription: String? = ""
)