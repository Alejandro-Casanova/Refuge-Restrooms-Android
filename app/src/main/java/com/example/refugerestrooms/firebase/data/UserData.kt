package com.example.refugerestrooms.firebase.data

data class UserData (
    val userName:String?,
    val latitude: Double?,
    val longitude:Double?
) {
    //Note: this is needed to read the data from the firebase database
    //firebase database throws this exception: UserData does not define a no-argument constructor
    constructor() : this(null, null, null)
}
