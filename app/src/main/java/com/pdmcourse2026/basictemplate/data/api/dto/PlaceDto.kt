package com.pdmcourse2026.basictemplate.data.api.dto

import com.pdmcourse2026.basictemplate.models.Place
import kotlinx.serialization.Serializable


@Serializable
data class PlaceDto(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val votes: Int
)

fun PlaceDto.toModel() = Place(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes
)
