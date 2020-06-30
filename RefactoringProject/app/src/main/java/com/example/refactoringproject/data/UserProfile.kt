package com.example.refactoringproject.data

data class UserProfile(
    val resultCode: String,
    val message: String,
    val item: UserItem
)

data class UserItem(
    val id: String,
    val nickName: String,
    val name: String,
    val email: String
)