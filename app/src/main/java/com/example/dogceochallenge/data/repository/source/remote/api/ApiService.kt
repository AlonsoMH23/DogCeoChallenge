package com.example.dogceochallenge.data.repository.source.remote.api

import com.example.dogceochallenge.data.repository.source.remote.response.BreedsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/api/breeds/list")
    suspend fun fetchBreeds(): BreedsResponse

    @GET("/api/breed/{breedName}/images")
    suspend fun fetchBreedImagesByBreedName(@Path("breedName") breedName: String): BreedsResponse
}