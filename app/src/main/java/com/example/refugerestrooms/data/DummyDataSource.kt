package com.example.refugerestrooms.data
import com.example.refugerestrooms.R
import com.example.refugerestrooms.model.Restroom

class DummyDataSource {
    fun loadDummyRestrooms(): List<Restroom> {
        return listOf<Restroom>(
            Restroom(
                R.string.restroom_name_1,
                R.string.restroom_address_1,
                R.string.restroom_distance_1,
                R.bool.restroom_unisex_1,
                R.bool.restroom_accessible_1
            ),
            Restroom(
                R.string.restroom_name_2,
                R.string.restroom_address_2,
                R.string.restroom_distance_2,
                R.bool.restroom_unisex_2,
                R.bool.restroom_accessible_2
            ),
            Restroom(
                R.string.restroom_name_3,
                R.string.restroom_address_3,
                R.string.restroom_distance_3,
                R.bool.restroom_unisex_3,
                R.bool.restroom_accessible_3
            ),
            Restroom(
                R.string.restroom_name_4,
                R.string.restroom_address_4,
                R.string.restroom_distance_4,
                R.bool.restroom_unisex_4,
                R.bool.restroom_accessible_4
            ))
    }
}