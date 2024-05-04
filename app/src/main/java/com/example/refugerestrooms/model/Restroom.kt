package com.example.refugerestrooms.model

import androidx.annotation.BoolRes
import androidx.annotation.StringRes

data class Restroom(
    @StringRes val nameStringResourceId: Int,
    @StringRes val addressStringResourceId: Int,
    @StringRes val distanceStringResourceId: Int,

    @BoolRes val unisexFlagResourceId: Int,
    @BoolRes val accessibleFlagResourceId: Int,
)
