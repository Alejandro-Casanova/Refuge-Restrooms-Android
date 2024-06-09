package com.example.refugerestrooms.firebase.data

fun createUserData(userName: String, latitude: Double, longitude: Double) : UserData {
    return UserData(
        userName,
        latitude,
        longitude
    )
}