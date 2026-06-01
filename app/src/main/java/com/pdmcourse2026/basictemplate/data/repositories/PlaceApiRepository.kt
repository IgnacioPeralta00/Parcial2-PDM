package com.pdmcourse2026.basictemplate.data.repositories

import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.api.dto.PlaceDto
import com.pdmcourse2026.basictemplate.data.api.dto.PostRequestDto
import com.pdmcourse2026.basictemplate.data.api.dto.PostResponseDto
import com.pdmcourse2026.basictemplate.data.api.dto.toModel
import com.pdmcourse2026.basictemplate.models.Place
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class PlaceApiRepository : PlaceRepository {
    override suspend fun getPlaces(): Result<List<Place>> {
        try {
            val response : List<PlaceDto> = KtorClient.client.get("options").body()
            return Result.success(response.map { placeDto -> placeDto.toModel() })
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun votePlace(placeId: Int): Result<Boolean> {
        try {
            val request = PostRequestDto(
                optionId = placeId
            )
            val response : PostResponseDto = KtorClient.client.post("vote") {
                setBody(request)
            }.body()
            return Result.success(response.ok)
        }
        catch (e: Exception) {
            return Result.failure(e)
        }
    }

}

/*
override suspend fun postComment(comment: Comment): Comment {
        val request = PostJsonRequestDto(
            title = comment.title,
            body = comment.body,
            userId = comment.userId
        )
        val response : JsonDto = KtorClient.client.post("posts") {
            setBody(request)
        }.body()
        return response.toModel()
    }
}
**/