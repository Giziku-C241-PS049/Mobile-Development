package com.example.giziku.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val titles :String,
    val description: String,
    val photo: Int,
    val author: String,
    val publish: String,
) : Parcelable