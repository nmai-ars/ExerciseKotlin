package com.example.woofapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.woofapp.R

/**
 * Data class đại diện cho Dog
 */
data class Dog(
    @DrawableRes val imageResourceId: Int,
    @StringRes val name: Int,
    val age: Int
)

/**
 * Danh sách dogs
 */
val dogs = listOf(
    Dog(R.drawable.dog1, R.string.dog_name_1, 2),
    Dog(R.drawable.tuyen, R.string.dog_name_2, 16),
    Dog(R.drawable.nhum, R.string.dog_name_3, 2),
    Dog(R.drawable.ngan, R.string.dog_name_4, 8),
    Dog(R.drawable.mi, R.string.dog_name_5, 8),
    Dog(R.drawable.dog2, R.string.dog_name_6, 14),
    Dog(R.drawable.dog4, R.string.dog_name_7, 2),
    Dog(R.drawable.dog5, R.string.dog_name_8, 7)
)