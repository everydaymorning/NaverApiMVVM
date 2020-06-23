package com.example.refactoringproject.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Shopping(
    val title: String,
    val link: String,
    val image: String,
    val lprice: Int,
    val mallName: String,
    val maker: String,
    val brand: String
): Parcelable

data class ShoppingItem(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<Shopping>
)