package com.example.dogceochallenge.data.repository.source.remote.response

import com.squareup.moshi.Json

data class BreedsResponse(
    @Json(name = "status")
    val status: String,
    @Json(name = "message")
    val message: List<String>
)

