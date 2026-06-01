package com.pdmcourse2026.basictemplate.data.repositories

import com.pdmcourse2026.basictemplate.models.Place

interface PlaceRepository {
    suspend fun getPlaces(): Result<List<Place>>
    suspend fun votePlace(placeId: Int): Result<Boolean>
}