package com.example.refugerestrooms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//data class Restroom(
//    @StringRes val nameStringResourceId: Int,
//    @StringRes val addressStringResourceId: Int,
//    @StringRes val distanceStringResourceId: Int,
//
//    @BoolRes val unisexFlagResourceId: Int,
//    @BoolRes val accessibleFlagResourceId: Int,
//
//    @IntegerRes val ratingResourceId: Int,
//)

@Serializable
data class Restroom(
    val id: Int,
    val name: String,
    val street: String,
    val city: String,
    val state: String,

    val accessible: Boolean,
    val unisex: Boolean,

    val directions: String,
    val comment: String,

    val latitude: Double,
    val longitude: Double,

    @SerialName(value = "created_at")
    val createdAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String,

    @SerialName(value = "downvote")
    val downVote: Int,
    @SerialName(value = "upvote")
    val upVote: Int,

    val country: String,

    @SerialName(value = "changing_table")
    val changingTable: Boolean,

    @SerialName(value = "edit_id")
    val editId: Int,

    val approved: Boolean,

    )